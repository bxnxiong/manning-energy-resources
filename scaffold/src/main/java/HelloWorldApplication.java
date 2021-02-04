//import db.UserDAO;

import db.DeviceDAO;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import resources.DeviceResource;
import resources.HelloWorldResource;


public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    // nothing to do yet
    bootstrap.addCommand(new StreamAppCommand());
  }

  @Override
  public void run(HelloWorldConfiguration configuration,
                  Environment environment) {
    final HelloWorldResource resource = new HelloWorldResource(
            configuration.getTemplate(),
            configuration.getDefaultName()
    );
    final TemplateHealthCheck healthCheck =
            new TemplateHealthCheck(configuration.getTemplate());
    environment.healthChecks().register("template", healthCheck);
    environment.jersey().register(resource);

    final JdbiFactory factory = new JdbiFactory();
    final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
    final DeviceDAO dao = jdbi.onDemand(DeviceDAO.class);
    environment.jersey().register(new DeviceResource(dao));

//    dao.addDevice("1", true);

  }

}