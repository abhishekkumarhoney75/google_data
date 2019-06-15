package honey.google.maps;

public class Activity {
    private String confidence;

    private String type;

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "honey.google.maps.Activity{" +
                "confidence='" + confidence + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}