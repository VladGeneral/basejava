package com.urice.webapp.util;

import com.urice.webapp.model.AbstractSection;
import com.urice.webapp.model.Resume;
import com.urice.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urice.webapp.ResumeTestData.*;
import static org.junit.Assert.*;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection abstractSection1 = new TextSection("OBJ");
        String json = JsonParser.write(abstractSection1, AbstractSection.class);
        System.out.println(json);
        AbstractSection abstractSection2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(abstractSection1, abstractSection2);
    }
}