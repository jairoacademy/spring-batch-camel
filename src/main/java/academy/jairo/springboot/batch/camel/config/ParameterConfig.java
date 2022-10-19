package academy.jairo.springboot.batch.camel.config;

import academy.jairo.springboot.batch.camel.exception.ParameterLoadPropertyException;
import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import academy.jairo.springboot.batch.camel.model.entity.Parameter;
import academy.jairo.springboot.batch.camel.repository.ParameterRepository;
import academy.jairo.springboot.batch.camel.validation.ParameterValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Configuration
@Slf4j
public class ParameterConfig {

    @Bean
    public ParameterDTO parameters(ParameterRepository parameterRepository) {

        ParameterDTO dto = new ParameterDTO();
        List<Parameter> list = parameterRepository.findByActivated("Y");
        for (Parameter parameter : list) {
            ReflectionUtils.doWithFields(dto.getClass(), field -> {
                if (field.isAnnotationPresent(ParameterValue.class)) {
                    ParameterValue annotation = field.getAnnotation(ParameterValue.class);
                    if (annotation.key().equals(parameter.getKey())) {
                        try {
                            BeanUtils.copyProperty(dto, field.getName(), parameter.getValue());
                            log.info("Parameter {} = {}", field.getName(), parameter.getValue());
                        } catch (InvocationTargetException e) {
                            throw new ParameterLoadPropertyException(e.getMessage());
                        }
                    }
                }
            });
        }

        return dto;
    }

}
