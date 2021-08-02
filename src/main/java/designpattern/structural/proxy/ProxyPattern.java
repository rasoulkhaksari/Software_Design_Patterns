package designpattern.structural.proxy;

import java.util.Arrays;
import java.util.List;

class Video {
    int id;
    String title;

    public Video(int id, String title) {
        this.id = id;
        this.title = title;
    }
}

interface ThirdPartyYoutubeLib {
    List<Video> listVideos();

    Video getVideoInfo(int id);

    Video downloadVideo(int id);
}

class ThirdPartyYoutubeClass implements ThirdPartyYoutubeLib {
    private List<Video> videos;

    public ThirdPartyYoutubeClass() {
        this.videos = Arrays.asList(new Video(10, "Star war"), new Video(12, "Jomanji"));
    }

    @Override
    public List<Video> listVideos() {
        System.out.println("Send an API request to YouTube and get list of videos");
        return this.videos;
    }

    @Override
    public Video getVideoInfo(int id) {
        System.out.println("Get metadata about some video");
        return this.videos.stream().filter(v -> v.id == id).findFirst().orElse(null);
    }

    @Override
    public Video downloadVideo(int id) {
        System.out.println("Download a video file from YouTube");
        return this.videos.stream().filter(v -> v.id == id).findFirst().orElse(null);
    }
}

class CachedYoutubeClass implements ThirdPartyYoutubeLib {
    private ThirdPartyYoutubeLib service;
    private List<Video> listCache;
    private Video videoCache;

    boolean needReset;

    public CachedYoutubeClass(ThirdPartyYoutubeLib service) {
        this.service = service;
    }

    @Override
    public List<Video> listVideos() {
        if (listCache == null || needReset) {
            listCache = service.listVideos();
        }
        return listCache;
    }

    @Override
    public Video getVideoInfo(int id) {
        if (videoCache == null || needReset) {
            videoCache = service.getVideoInfo(id);
        }
        return videoCache;
    }

    private boolean downloadExists(int id) {
        return this.listCache.stream().anyMatch(v -> v.id == id);
    }

    @Override
    public Video downloadVideo(int id) {
        if (!downloadExists(id) || needReset) {
            return service.downloadVideo(id);
        } else {
            return this.listCache.stream().filter(v -> v.id == id).findFirst().orElse(null);
        }
    }

}

class YoutubeManager {
    private ThirdPartyYoutubeLib service;

    public YoutubeManager(ThirdPartyYoutubeLib service) {
        this.service = service;
    }

    public void renderVideoPage(int id) {
        Video info = service.getVideoInfo(id);
        System.out.println("Render the video page.");
    }

    public void renderListPanel() {
        List<Video> list = service.listVideos();
        System.out.println("Render the list of video thumbnails");
    }

    public void reactOnUserInput() {
        renderVideoPage(0);
        renderListPanel();
    }
}

public class ProxyPattern {
    public static void demo() {
        ThirdPartyYoutubeLib aYouTubeService = new ThirdPartyYoutubeClass();
        ThirdPartyYoutubeLib aTouTubeProxy = new CachedYoutubeClass(aYouTubeService);
        YoutubeManager manager = new YoutubeManager(aTouTubeProxy);
        manager.reactOnUserInput();
    }
}
