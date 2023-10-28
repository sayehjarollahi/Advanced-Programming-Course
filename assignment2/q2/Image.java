public class Image extends File {
    private String resolution;
    private String extension;

    public Image(String name, Keeper parent, int size, String resolution, String extension) {
        super(name, parent, size);
        this.extension = extension;
        this.resolution = resolution;
    }

    public Image(Image photo, Keeper parent) {
        super(photo, parent);
        resolution = photo.resolution;
        extension = photo.extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getResolution() {
        return resolution;
    }

    @Override
    public String getType() {
        return "img";
    }

    @Override
    public String toString() {
        return this.name + " img " + this.size + "MB";
    }
}
