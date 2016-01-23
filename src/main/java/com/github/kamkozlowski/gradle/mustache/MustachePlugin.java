package com.github.kamkozlowski.gradle.mustache;

import org.gradle.api.Project;

public class MustachePlugin implements org.gradle.api.Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.getExtensions().create("mustache", MustacheExtension.class);
        target.getTasks().create("compileTemplate", CompileTemplateTask.class);
    }
}