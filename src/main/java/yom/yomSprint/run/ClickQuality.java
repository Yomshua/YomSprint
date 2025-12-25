package yom.yomSprint.run;

public enum ClickQuality {

    PERFECT(0.7), GOOD(0.5),OK(0.4),BAD(0.2);

    double quality;

    ClickQuality(double quality) {
        this.quality = quality;
    }

    public double getQuality() {
        return quality;
    }
}
