package factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by heleninsa on 2017/5/13.
 */
public abstract class SpringFactory {

    protected final static ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("factory/bean_factory.xml");

    protected static <T> T getBeanByClass(Class<T> beanClass) {
        T bean = CONTEXT.getBean(beanClass);
        return bean;
    }

}
