package it.mgg.siapafismw.configurations;

import java.util.Arrays;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;


@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//@EnableJpaRepositories(basePackages = "it.mgg.siapafismw.dao")
public class PersistenceJNDIConfig 
{
	@Autowired
    private Environment env;
	
	private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private final String PROPERTY_DIALECT = "hibernate.dialect";

//    @Bean
//    LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
//		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
//		lfb.setDataSource(dataSource());
//		lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//		lfb.setPackagesToScan("it.mgg.siapafismw.model");
//		lfb.setJpaProperties(hibernateProps());
//		return lfb;
//	}
//    
//    Properties hibernateProps() {
//		Properties properties = new Properties();
//		properties.setProperty(PROPERTY_DIALECT, env.getProperty(PROPERTY_DIALECT));
//		properties.setProperty(PROPERTY_SHOW_SQL, env.getProperty(PROPERTY_SHOW_SQL));
//		return properties;
//	}
    
    @Bean
    public DataSource dataSource() throws NamingException 
    {
    	String profiloAttivo = env.getProperty("siapafismw.profiles.active");
    	
    	/* costruzione del datasource sulla base del profilo */
    	if(StringUtils.isBlank(profiloAttivo))
    	{
    		/* profilo non specificato nel file di property, impossibile inizializzare il datasource */
    		throw new IllegalArgumentException("Profilo non definito, impossibile inizializzare il datasource");
    	}
    	
    	else if(!Arrays.asList("dev", "wildfly").contains(profiloAttivo.toLowerCase()))
    	{
    		/* valore el profilo non e' fra quelli predefiniti */
    		throw new IllegalArgumentException("Il valore del profilo " + profiloAttivo + " non e' tra quelli predefiniti");
    	}
    	
    	else if(profiloAttivo.equalsIgnoreCase("wildfly"))
    	{
    		/* lettura property */
    		String jdbcUrl = env.getProperty("jdbc.url");
    		if(StringUtils.isBlank(jdbcUrl))
    			throw new IllegalArgumentException("URL per connessione JNDI non definita");
    		
    		/* attivazione datasource JNDI */
    		//return (DataSource) new JndiTemplate().lookup(env.getProperty(jdbcUrl));
    		return (DataSource) new JndiTemplate().lookup("java:/middleware");
    	}
    	
    	else
    	{
    		/* lettura properties */
    		String driverClassName = env.getProperty("siapafismw.datasource.driver-class-name");
    		if(StringUtils.isBlank(driverClassName))
    			throw new IllegalArgumentException("Driver class name non definito");
    		
    		String url = env.getProperty("siapafismw.datasource.url");
    		if(StringUtils.isBlank(url))
    			throw new IllegalArgumentException("URL datasource non definito");
    		
    		String username = env.getProperty("siapafismw.datasource.username");
    		if(StringUtils.isBlank(username))
    			throw new IllegalArgumentException("URL datasource non definito");
    		
    		String password = env.getProperty("siapafismw.datasource.password");
    		if(StringUtils.isBlank(password))
    			throw new IllegalArgumentException("Password non definita");
    		
    		/* attivazione datasource da properties */
    		DriverManagerDataSource dataSource = new DriverManagerDataSource();
    		dataSource.setDriverClassName(driverClassName);
    		dataSource.setUrl(url);
    		dataSource.setUsername(username);
    		dataSource.setPassword(password);
//    		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//    		dataSourceBuilder.driverClassName(driverClassName);
//    		dataSourceBuilder.url(url);
//    		dataSourceBuilder.username(username);
//    		dataSourceBuilder.password(password);
//    		
//    		return dataSourceBuilder.build();
    		
    		return dataSource;
    	}
    	
        
    }

//    @Bean
//    JpaTransactionManager transactionManager() throws NamingException {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return transactionManager;
//	}
}
