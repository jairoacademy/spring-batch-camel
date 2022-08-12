package academy.jairo.springboot.camel.demo.config;

import academy.jairo.springboot.camel.demo.model.dto.ParameterDTO;
import academy.jairo.springboot.camel.demo.model.entity.Parameter;
import academy.jairo.springboot.camel.demo.repository.ParameterRepository;
import academy.jairo.springboot.camel.demo.validation.ParameterValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Configuration
@Slf4j
public class ParameterConfig {
    @Autowired
    private ParameterRepository parameterRepository;

    @Bean
    public ParameterDTO parameters() {
        ParameterDTO dto = new ParameterDTO();
        List<Parameter> list = parameterRepository.findByActivated("Y");

        list.forEach(parameter -> {
            ReflectionUtils.doWithFields(dto.getClass(), field -> {
                if (field.isAnnotationPresent(ParameterValue.class)) {
                    ParameterValue annotation = field.getAnnotation(ParameterValue.class);
                    if (annotation.key().equals(parameter.getKey())) {
                        try {
                            BeanUtils.copyProperty(dto, field.getName(), parameter.getValue());
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });

        return dto;
    }

}
