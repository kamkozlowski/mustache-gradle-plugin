package com.github.kamkozlowski.gradle.mustache;

import java.net.URI;

public class TemplateDefinition {

    private URI source = null;
    private URI target = null;
    private URI context = null;

    public TemplateDefinition(URI source, URI target) {
        this.source = source;
        this.target = target;
    }

    public TemplateDefinition(URI source, URI target, URI context) {

        this.source = source;
        this.target = target;
        this.context = context;
    }

    public URI getSource() {
        return source;
    }

    public void setSource(URI source) {
        this.source = source;
    }

    public URI getContext() {
        return context;
    }

    public void setContext(URI context) {
        this.context = context;
    }

    public URI getTarget() {
        return target;
    }

    public void setTarget(URI target) {
        this.target = target;
    }
}
