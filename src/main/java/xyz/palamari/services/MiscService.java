package xyz.palamari.services;

import io.quarkus.qute.TemplateExtension;
import org.eclipse.microprofile.config.ConfigProvider;

@TemplateExtension(namespace = "misc")
public class MiscService {

    static String getVersion() {
        return ConfigProvider.getConfig().getValue("quarkus.application.version", String.class);
    }
}
