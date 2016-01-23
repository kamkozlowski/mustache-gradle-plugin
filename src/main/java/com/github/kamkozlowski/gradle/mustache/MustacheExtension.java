package com.github.kamkozlowski.gradle.mustache;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class MustacheExtension {

    private List<TemplateDefinition> templateDefinitions = new ArrayList<TemplateDefinition>();
    private URI context = null;

    public void template(java.net.URI source, java.net.URI target,java.net.URI context){
        templateDefinitions.add(new TemplateDefinition(source,target,context));
    }

    public void template(java.net.URI source, java.net.URI context) {
        templateDefinitions.add(new TemplateDefinition(source, context));
    }

    public URI getContext() {
        return context;
    }

    public void setContext(URI context) {
        this.context = context;
    }

    public List<TemplateDefinition> getTemplateDefinitions() {
        return templateDefinitions;
    }
}
