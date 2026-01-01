package yom.yomSprint.clicks;

public enum ClickQuality {

    PERFECT(0.7), GOOD(0.5),OK(0.3),BAD(0.1);

    double quality;

    ClickQuality(double quality) {
        this.quality = quality;
    }

    public double getQuality() {
        return quality;
    }
}
