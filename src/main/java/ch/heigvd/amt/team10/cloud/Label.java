package ch.heigvd.amt.team10.cloud;

/**
 * @author Nicolas Crausaz & Maxime Scharwath
 * @param name The name of the label
 * @param confidence The confidence of the label (between 0 and 1)
 */
public record Label(String name, float confidence) {
    @Override
    public String toString() {
        return String.format("%s (%.2f%%)", name, confidence * 100);
    }
}
