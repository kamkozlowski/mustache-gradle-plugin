package com.github.kamkozlowski.gradle.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;

public class CompileTemplateTask extends DefaultTask {

    private static final String GROUP = "mustache";

    public CompileTemplateTask(){
        setGroup(GROUP);
    }

    @TaskAction
    public void compileTemplate() {
        MustacheExtension extension = getProject().getExtensions().findByType(MustacheExtension.class);
        if (extension == null) {
            extension = new MustacheExtension();
        }
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        Object rootContext = createContext(extension.getContext(),Charset.defaultCharset());
        for(TemplateDefinition templateDefinition : extension.getTemplateDefinitions()) {
            Reader reader = null;
            Object templateContext;
            if(templateDefinition.getContext()==null){
                templateContext = rootContext;
            }
            else{
                templateContext = createContext(templateDefinition.getContext(),Charset.defaultCharset());
            }
            try {
                reader = new BufferedReader(new FileReader(templateDefinition.getSource().getPath()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Mustache mustache = mustacheFactory.compile(reader, "template");
            try {
                mustache.execute(new FileWriter(templateDefinition.getTarget().getPath()), templateContext).flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Object createContext(URI contextConfiguration, Charset charset)   {
        if (contextConfiguration == null) {
            return null;
        }
        Yaml yaml;
        try {
            yaml = new Yaml();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        try {
            Reader reader = new InputStreamReader(new FileInputStream(contextConfiguration.getPath()), charset);
            return yaml.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
