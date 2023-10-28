public class Video extends File {
    private String quality;
    private String videoLength;

    public Video(String name, Keeper parent, int size, String quality, String videoLength) {
        super(name, parent, size);
        this.quality = quality;
        this.videoLength = videoLength;
    }

    public Video(Video vid, Keeper parent) {
        super(vid, parent);
        quality = vid.quality;
        videoLength = vid.videoLength;
    }

    public String getQuality() {
        return quality;
    }

    public String getVideoLength() {
        return videoLength;
    }

    @Override
    public String getType() {
        return "mp4";
    }

    @Override
    public String toString() {
        return this.name + " mp4 " + this.size + "MB";
    }
}
