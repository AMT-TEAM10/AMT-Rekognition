package ch.heigvd.amt.team10.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabelTest {

    @Test
    public void shouldGetJsonFromLabel() throws JsonProcessingException {
        Label label = new Label("test", 0.5f);
        assertEquals("{\"name\":\"test\",\"confidence\":0.5}", label.toJSON());
    }

    @Test
    public void shouldGetJsonFromLabelArray() throws IOException {
        Label[] labels = new Label[]{new Label("test", 0.5f), new Label("test2", 0.4f)};
        assertEquals("[{\"name\":\"test\",\"confidence\":0.5},{\"name\":\"test2\",\"confidence\":0.4}]", Label.toJSONArray(labels));
    }
}
