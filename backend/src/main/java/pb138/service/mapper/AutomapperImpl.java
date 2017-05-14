package pb138.service.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//Source: https://github.com/drichtarik/Dog-barbershop/blob/master/service/src/main/java/cz/muni/fi/pa165/service/BeanMappingServiceImpl.java

/**
 * {@inheritDoc}
 *
 * @author Dominik Gmiterko
 */
public class AutomapperImpl implements Automapper {

    private final Mapper dozer;

    /**
     * {@inheritDoc}
     */
    public AutomapperImpl(DozerBeanMapper dozer) {
        if (dozer == null) {
            throw new IllegalArgumentException("Unsatisfied dependency on dozer bean");
        }
        this.dozer = dozer;
        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozerJdk8Converters.xml");
        dozer.setMappingFiles(mappingFiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> mapTo(Collection<?> beanCollection, Class<T> targetClass) {
        if (beanCollection == null) {
            return null;
        }
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : beanCollection) {
            mappedCollection.add(mapTo(object, targetClass));
        }
        return mappedCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T mapTo(Object bean, Class<T> targetClass) {
        if (bean == null) {
            return null;
        }
        return dozer.map(bean, targetClass);
    }
}
