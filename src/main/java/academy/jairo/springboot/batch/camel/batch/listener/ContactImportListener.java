package academy.jairo.springboot.batch.camel.batch.listener;

import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import academy.jairo.springboot.batch.camel.util.FileStructureUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ContactImportListener implements ChunkListener {
    @NonNull
    private ParameterDTO parameters;

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
    }

    @SneakyThrows
    @Override
    public void afterChunk(ChunkContext chunkContext) {
        String resourcesToBeProcessed = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get("resourcesToBeProcessed")
                .toString();

        if (resourcesToBeProcessed != null & !resourcesToBeProcessed.isEmpty()) {
            List<String> resourcesToBeProcessedList = new ArrayList<>(Arrays.asList(resourcesToBeProcessed.split(",")));
            log.info("-> {} contact resource To Be final processed", resourcesToBeProcessedList.size());
            for (String resourceFilename : resourcesToBeProcessedList) {
                String moveFrom = FileUtils.getFile(parameters.getImportReceivedPath()).getPath();
                String moveTo = FileUtils.getFile(parameters.getImportProcessedPath()).getPath();
                FileStructureUtil.moveFile(moveFrom, moveTo, resourceFilename);
                log.info("-> The contact file {} was moved to processed folder!", resourceFilename);
            }
        }
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {

    }

}
