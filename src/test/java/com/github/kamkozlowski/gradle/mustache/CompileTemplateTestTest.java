package com.github.kamkozlowski.gradle.mustache;

import org.gradle.api.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.gradle.testfixtures.ProjectBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CompileTemplateTestTest {

    @Before
    public void setUp() throws Exception{
    }

    @After
    public void tearDown() {
    }

    @Test
    public void compileTemplateTaskTest() throws Exception{
        MustacheExtension mustacheExtension = new MustacheExtension();
        mustacheExtension.setContext(new URI("src/test/resources/properties.yaml"));
        mustacheExtension.template(new URI("src/test/resources/template.mustache"), new URI("build/output.txt"));
        Project p = ProjectBuilder.builder().build();
        p.getExtensions().add("MustacheExtension",mustacheExtension);
        CompileTemplateTask compileTemplateTask = p.getTasks().create("compileTemplate",CompileTemplateTask.class);
        compileTemplateTask.compileTemplate();
        assertEquals("Incorrect output file ","    * Name: Book\n" +
                "      Price: $10\n" +
                "      Feature: feature1\n" +
                "      Feature: feature2\n" +
                "    * Name: Car\n" +
                "      Price: $100\n" +
                "      Feature: feature1\n",readFile(new URI("build/output.txt").getPath(),Charset.defaultCharset()));
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
