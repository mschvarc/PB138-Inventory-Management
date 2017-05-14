package pb138.service.mapper;

import java.util.Collection;
import java.util.List;

// Original source (javadoc modified): https://github.com/drichtarik/Dog-barbershop/blob/master/service/src/main/java/cz/muni/fi/pa165/service/BeanMappingService.java

/**
 * Interface for mapping between DAL Entities and DTOs in Web layer
 *
 * @author Dominik Gmiterko, Martin Schvarcbacher
 */
public interface Automapper {

    /**
     * Maps a generic collection of one Bean type onto targetClass Bean
     *
     * @param beanCollection input
     * @param targetClass    target type
     * @param <Bean>         output target type
     * @return mapped collection
     */
    <Bean> List<Bean> mapTo(Collection<?> beanCollection, Class<Bean> targetClass);

    /**
     * Maps one bean to another
     *
     * @param bean        input
     * @param targetClass target type
     * @param <Bean>      output target type
     * @return mapped bean
     */
    <Bean> Bean mapTo(Object bean, Class<Bean> targetClass);
}
