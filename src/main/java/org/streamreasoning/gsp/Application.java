package org.streamreasoning.gsp;

import com.vaadin.flow.component.page.Push;
import org.springframework.context.ConfigurableApplicationContext;
import org.streamreasoning.gsp.data.TATableRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;
import org.streamreasoning.gsp.seraph.engine.Neo4jContinuousQueryExecutionImpl;
import org.streamreasoning.gsp.seraph.engine.QueryFactory;
import org.streamreasoning.gsp.seraph.syntax.SeraphQL;
import org.streamreasoning.gsp.services.SeraphService;
import org.streamreasoning.gsp.services.SeraphString;

import java.io.IOException;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Theme("rspview")
@Push
public class Application implements AppShellConfigurator {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties, TATableRepository repository) {

        // This bean ensures the database is only initialized when empty
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                if (repository.count() == 0L) {
                    return super.initializeDatabase();
                }
                return false;
            }
        };
    }
}
