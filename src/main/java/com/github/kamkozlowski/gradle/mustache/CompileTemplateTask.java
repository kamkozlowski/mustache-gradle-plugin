package com.github.kamkozlowski.gradle.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskAction;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;

public class CompileTemplateTask extends DefaultTask {

    private static final String GROUP = "mustache";
    private static Logger LOG = Logging.getLogger(CompileTemplateTask.class);

    public CompileTemplateTask(){
        setGroup(GROUP);
    }

    @TaskAction
    public void compileTemplate() {
        LOG.debug("Compile template execution runs");
        MustacheExtension extension = getProject().getExtensions().findByType(MustacheExtension.class);
        if (extension == null) {
            LOG.info("No configuration found. Using default parameters.");
            extension = new MustacheExtension();
        }
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        Object rootContext = null;
        if(extension.getContext()!=null){
            LOG.debug("Loading root context {}", extension.getContext());
            rootContext = createContext(extension.getContext(),Charset.defaultCharset());
        }
        else{
            LOG.debug("No root context found");
        }
        int templateDefinitionsAmount = extension.getTemplateDefinitions().size();
        if(templateDefinitionsAmount==0){
            LOG.warn("No template definitions found");
        }
        else{
            LOG.info("Template definitions found: {}", templateDefinitionsAmount);
        }
        for(TemplateDefinition templateDefinition : extension.getTemplateDefinitions()) {
            Reader reader = null;
            Object templateContext;
            if(templateDefinition.getContext()==null){
                if(rootContext==null){
                    LOG.error("Context not defined");
                    throw new RuntimeException("Context not defined");
                }
                LOG.debug("Using root context for template {}", templateDefinition.getSource());
                templateContext = rootContext;
            }
            else{
                LOG.debug("Using local context for template {}", templateDefinition.getSource());
                templateContext = createContext(templateDefinition.getContext(),Charset.defaultCharset());
            }
            try {
                reader = new BufferedReader(new FileReader(templateDefinition.getSource().getPath()));
            } catch (Exception e) {
                LOG.error("Error during load file");
                throw new RuntimeException(e);
            }
            Mustache mustache = mustacheFactory.compile(reader, "template");
            try {
                mustache.execute(new FileWriter(templateDefinition.getTarget().getPath()), templateContext).flush();
            } catch (IOException e) {
                LOG.error("Error during save output file");
                throw new RuntimeException(e);
            }
        }
    }

    private static Object createContext(URI contextConfiguration, Charset charset)   {
        Yaml yaml = new Yaml();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(contextConfiguration.getPath()), charset);
            return yaml.load(reader);
        } catch (IOException e) {
            LOG.error("Error during context creation");
            throw new RuntimeException(e);
        }
    }
}
