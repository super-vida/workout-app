package cz.prague.vida.strava.api;

import com.google.gson.Gson;
import cz.prague.vida.strava.authenticator.RefreshTokenResponse;
import cz.prague.vida.strava.entities.*;
import cz.prague.vida.strava.model.ActivityStats;
import cz.prague.vida.strava.model.DetailedActivity;
import cz.prague.vida.strava.model.DetailedAthlete;
import lombok.Data;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;

public class StravaV3Client implements StravaClient {

    private String accessToken;
    private DetailedAthlete currentAthlete;
    private Gson gson = new Gson();

    private Proxy proxy;

    private ProxySelector proxySelector;

    private StravaV3Client(Builder builder) throws URISyntaxException, InterruptedException, IOException {
        this.proxy = builder.proxy;
        this.proxySelector = builder.getProxySelector();
        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", builder.getClientId());
        formData.put("client_secret", builder.getClientSecret());
        formData.put("grant_type", "refresh_token");
        formData.put("refresh_token", builder.getRefreshToken());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.strava.com/api/v3/oauth/token"))
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();
        HttpResponse<String> response = null;
        if (proxySelector != null) {
            response = HttpClient
                    .newBuilder()
                    .proxy(proxySelector)
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } else {
            response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }
        RefreshTokenResponse activity = gson.fromJson(response.body(), RefreshTokenResponse.class);
        this.accessToken = activity.getAccessToken();
    }

    @Data
    public static class Builder {
        private String clientId;
        private String clientSecret;
        private String refreshToken;
        private Proxy proxy;
        private ProxySelector proxySelector;

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder withRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder withProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder withProxySelector(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
            return this;
        }

        public StravaV3Client build() throws IOException, URISyntaxException, InterruptedException {
            return new StravaV3Client(this);
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public DetailedAthlete getCurrentAthlete() {
        if (currentAthlete == null) {
            String URL = "https://www.strava.com/api/v3/athlete";
            String result = getResult(URL);
            currentAthlete = gson.fromJson(result, DetailedAthlete.class);
        }
        return currentAthlete;
    }

    @Override
    public Athlete updateAthlete(HashMap optionalParameters) {
        String URL = "https://www.strava.com/api/v3/athlete";
        String result = putResult(URL, optionalParameters);

        Athlete athlete = gson.fromJson(result, Athlete.class);

        return athlete;
    }

    @Override
    public DetailedAthlete findAthlete(int id) {

        String URL = "https://www.strava.com/api/v3/athletes/" + id;
        String result = getResult(URL);

        DetailedAthlete athlete = gson.fromJson(result, DetailedAthlete.class);

        return athlete;

    }

    @Override
    public List<SegmentEffort> findAthleteKOMs(int athleteId) {
        String URL = "https://www.strava.com/api/v3/athletes/" + athleteId + "/koms";
        String result = getResult(URL);

        SegmentEffort[] segmentEffortArray = gson.fromJson(result, SegmentEffort[].class);
        List<SegmentEffort> segmentEfforts = Arrays.asList(segmentEffortArray);
        return segmentEfforts;
    }

    @Override
    public ActivityStats getAthleteStats(Long athleteId) {
        String URL = "https://www.strava.com/api/v3/athletes/" + athleteId + "/stats";
        return gson.fromJson(getResult(URL), ActivityStats.class);
    }

    @Override
    public List<SegmentEffort> findAthleteKOMs(int athleteId, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athletes/" + athleteId + "/koms?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        SegmentEffort[] segmentEffortArray = gson.fromJson(result, SegmentEffort[].class);
        List<SegmentEffort> segmentEfforts = Arrays.asList(segmentEffortArray);
        return segmentEfforts;
    }

    @Override
    public List<Athlete> getCurrentAthleteFriends() {
        String URL = "https://www.strava.com/api/v3/athlete/friends";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> getCurrentAthleteFriends(int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athlete/friends?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteFriends(int id) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/friends";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteFriends(int id, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/friends?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> getCurrentAthleteFollowers() {
        String URL = "https://www.strava.com/api/v3/athlete/followers";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> getCurrentAthleteFollowers(int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athlete/followers?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteFollowers(int id) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/followers";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteFollowers(int id, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/followers?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteBothFollowing(int id) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/both-following";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findAthleteBothFollowing(int id, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athletes/" + id + "/both-following?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public Activity createActivity(String name, String type, String start_date_local, int elapsed_time) {
        String URL = "https://www.strava.com/api/v3/activities?name=" + name + "&type=" + type + "&start_date_local=" + start_date_local + "&elapsed_time=" + elapsed_time;
        String result = postResult(URL);

        System.out.println("RESULTADO" + result);
        Activity activity = gson.fromJson(result, Activity.class);
        return activity;
    }

    @Override
    public Activity createActivity(String name, String type, String start_date_local, int elapsed_time, String description, float distance) {
        String URL = "https://www.strava.com/api/v3/activities?name=" + name + "&type=" + type + "&start_date_local=" + start_date_local + "&elapsed_time=" + elapsed_time + "&description=" + description + "&distance=" + distance;
        String result = postResult(URL);

        Activity activity = gson.fromJson(result, Activity.class);
        return activity;
    }

    @Override
    public void deleteActivity(long activityId) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId;
        String result = deleteResult(URL);

        gson.fromJson(result, String.class);

    }

    @Override
    public DetailedActivity findActivity(long id) {
        String URL = "https://www.strava.com/api/v3/activities/" + id;
        String result = getResult(URL);
        DetailedActivity activity = gson.fromJson(result, DetailedActivity.class);
        return activity;
    }

    @Override
    public Activity findActivity(long id, boolean include_all_efforts) {
        String URL = "https://www.strava.com/api/v3/activities/" + id + "?include_all_efforts=" + include_all_efforts;
        String result = getResult(URL);

        Activity activity = gson.fromJson(result, Activity.class);

        return activity;
    }

    @Override
    public Activity updateActivity(long activityId, HashMap optionalParameters) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId;
        String result = putResult(URL, optionalParameters);

        Activity activity = gson.fromJson(result, Activity.class);

        return activity;
    }

    @Override
    public List<DetailedActivity> getCurrentAthleteActivitiesAll() {
        int resultsPerPage = 30;
        int page = 1;
        List<DetailedActivity> currentActivities = new ArrayList<DetailedActivity>();
        List<DetailedActivity> activitiesPerPage;

        while ((activitiesPerPage = this.getCurrentAthleteActivities(page, resultsPerPage)).size() > 0) {
            currentActivities.addAll(activitiesPerPage);
            page++;
        }
        return currentActivities;
    }

    @Override
    public List<DetailedActivity> getCurrentAthleteActivities() {
        String URL = "https://www.strava.com/api/v3/athlete/activities";
        String result = getResult(URL);

        DetailedActivity[] activitiesArray = gson.fromJson(result, DetailedActivity[].class);
        List<DetailedActivity> currentActivities = Arrays.asList(activitiesArray);
        return currentActivities;
    }

    @Override
    public List<DetailedActivity> getCurrentAthleteActivities(int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/athlete/activities?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        DetailedActivity[] activitiesArray = gson.fromJson(result, DetailedActivity[].class);
        List<DetailedActivity> currentActivities = Arrays.asList(activitiesArray);
        return currentActivities;
    }

    @Override
    public List<Activity> getCurrentAthleteActivitiesBeforeDate(long before) {
        String URL = "https://www.strava.com/api/v3/athlete/activities?before=" + before;
        String result = getResult(URL);

        Activity[] activitiesArray = gson.fromJson(result, Activity[].class);
        List<Activity> currentActivities = Arrays.asList(activitiesArray);
        return currentActivities;
    }

    @Override
    public List<Activity> getCurrentAthleteActivitiesAfterDate(long after) {
        String URL = "https://www.strava.com/api/v3/athlete/activities?after=" + after;
        String result = getResult(URL);

        Activity[] activitiesArray = gson.fromJson(result, Activity[].class);
        List<Activity> currentActivities = Arrays.asList(activitiesArray);
        return currentActivities;
    }

    public List<DetailedActivity> getCurrentFriendsActivities() {
        String URL = "https://www.strava.com/api/v3/activities/following";
        String result = getResult(URL);

        DetailedActivity[] activitiesArray = gson.fromJson(result, DetailedActivity[].class);
        List<DetailedActivity> currentFriendsActivities = Arrays.asList(activitiesArray);
        return currentFriendsActivities;
    }

    public List<Activity> getCurrentFriendsActivities(int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/activities/following?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Activity[] activitiesArray = gson.fromJson(result, Activity[].class);
        List<Activity> currentFriendsActivities = Arrays.asList(activitiesArray);
        return currentFriendsActivities;
    }

    @Override
    public List<ActivityZone> getActivityZones(long activityId) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/zones";
        String result = getResult(URL);

        ActivityZone[] zonesArray = gson.fromJson(result, ActivityZone[].class);
        List<ActivityZone> zones = Arrays.asList(zonesArray);
        return zones;
    }

    @Override
    public List<LapsItem> findActivityLaps(long activityId) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/laps";
        String result = getResult(URL);

        LapsItem[] LapsItemsArray = gson.fromJson(result, LapsItem[].class);
        List<LapsItem> LapsItems = Arrays.asList(LapsItemsArray);
        return LapsItems;
    }

    @Override
    public List<Comment> findActivityComments(long activityId) {

        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/comments";
        String result = getResult(URL);

        Comment[] commentsArray = gson.fromJson(result, Comment[].class);

        List<Comment> comments = Arrays.asList(commentsArray);

        return comments;
    }

    @Override
    public List<Comment> findActivityComments(long activityId, boolean markdown, int page, int per_page) {

        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/comments?markdown=" + markdown + "&page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Comment[] commentsArray = gson.fromJson(result, Comment[].class);

        List<Comment> comments = Arrays.asList(commentsArray);

        return comments;
    }

    @Override
    public List<Athlete> findActivityKudos(long activityId) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/kudos";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findActivityKudos(long activityId, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/kudos?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findClubMembers(int clubId) {
        String URL = "https://www.strava.com/api/v3/clubs/" + clubId + "/members";
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Athlete> findClubMembers(int clubId, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/clubs/" + clubId + "/members?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Athlete[] athletesArray = gson.fromJson(result, Athlete[].class);

        List<Athlete> athletes = Arrays.asList(athletesArray);

        return athletes;
    }

    @Override
    public List<Activity> findClubActivities(int clubId) {
        String URL = "https://www.strava.com/api/v3/clubs/" + clubId + "/activities";
        String result = getResult(URL);

        Activity[] activitiesArray = gson.fromJson(result, Activity[].class);
        List<Activity> clubActivities = Arrays.asList(activitiesArray);
        return clubActivities;
    }

    @Override
    public List<Activity> findClubActivities(int clubId, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/clubs/" + clubId + "/activities" + "?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        Activity[] activitiesArray = gson.fromJson(result, Activity[].class);
        List<Activity> clubActivities = Arrays.asList(activitiesArray);
        return clubActivities;
    }

    @Override
    public Club findClub(int id) {
        String URL = "https://www.strava.com/api/v3/clubs/" + id;
        String result = getResult(URL);

        Club club = gson.fromJson(result, Club.class);

        return club;
    }

    public List<Club> getCurrentAthleteClubs() {
        String URL = "https://www.strava.com/api/v3/athlete/clubs";
        String result = getResult(URL);

        Club[] clubsArray = gson.fromJson(result, Club[].class);

        List<Club> clubs = Arrays.asList(clubsArray);

        return clubs;
    }

    @Override
    public Gear findGear(String id) {
        String URL = "https://www.strava.com/api/v3/gear/" + id;
        String result = getResult(URL);

        Gear gear = gson.fromJson(result, Gear.class);

        return gear;
    }

    @Override
    public Route findRoute(int routeId) {

        String URL = "https://www.strava.com/api/v3/route/" + routeId;
        String result = getResult(URL);

        Route route = gson.fromJson(result, Route.class);

        return route;

    }

    @Override
    public List<Route> findAthleteRoutes(int athleteId) {
        String URL = "https://www.strava.com/api/v3/athletes/" + athleteId + "/routes";
        String result = getResult(URL);

        Route[] routesArray = gson.fromJson(result, Route[].class);
        List<Route> athleteRoutes = Arrays.asList(routesArray);
        return athleteRoutes;
    }

    @Override
    public Segment findSegment(long segmentId) {
        String URL = "https://www.strava.com/api/v3/segments/" + segmentId;
        String result = getResult(URL);

        Segment segment = gson.fromJson(result, Segment.class);
        return segment;
    }

    public List<Segment> getCurrentStarredSegment() {
        String URL = "https://www.strava.com/api/v3/segments/starred";
        String result = getResult(URL);

        Segment[] segmentsArray = gson.fromJson(result, Segment[].class);

        List<Segment> segments = Arrays.asList(segmentsArray);

        return segments;
    }

    @Override
    public SegmentLeaderBoard findSegmentLeaderBoard(long segmentId) {
        String URL = "https://www.strava.com/api/v3/segments/" + segmentId + "/leaderboard";
        String result = getResult(URL);

        SegmentLeaderBoard segmentLeaderBoard = gson.fromJson(result, SegmentLeaderBoard.class);
        return segmentLeaderBoard;
    }

    @Override
    public SegmentLeaderBoard findSegmentLeaderBoard(long segmentId, int page, int per_page) {
        String URL = "https://www.strava.com/api/v3/segments/" + segmentId + "/leaderboard?page=" + page + "&per_page=" + per_page;
        String result = getResult(URL);

        SegmentLeaderBoard segmentLeaderBoard = gson.fromJson(result, SegmentLeaderBoard.class);
        return segmentLeaderBoard;
    }

    @Override
    public SegmentLeaderBoard findSegmentLeaderBoard(long segmentId, HashMap optionalParameters) {
        String URL = "https://www.strava.com/api/v3/segments/" + segmentId + "/leaderboard";
        String result = getResult(URL, optionalParameters);

        SegmentLeaderBoard segmentLeaderBoard = gson.fromJson(result, SegmentLeaderBoard.class);
        return segmentLeaderBoard;
    }

    @Override
    public List<Segment> findSegments(double[] bound) {
        String URL = "https://www.strava.com/api/v3/segments/explore?bounds=" + bound.toString();
        String result = getResult(URL);

        //////////UGLY HACK TO ALLOW GSON TO PARSE THE JSON STRING AND RETURN A LIST OF SEGMENTS

        String segmentString = "\\{\"segments\":";

        result = result.replaceFirst(segmentString, "");
        result = result.substring(0, result.lastIndexOf("}"));

        Segment[] segmentsArray = gson.fromJson(result, Segment[].class);
        List<Segment> segments = Arrays.asList(segmentsArray);
        return segments;
    }

    @Override
    public List<Segment> findSegments(double[] bounds, HashMap optionalParameters) {
        String URL = "https://www.strava.com/api/v3/segments/explore?bounds=" + bounds.toString();
        String result = getResult(URL, optionalParameters);

        //////////UGLY HACK TO ALLOW GSON TO PARSE THE JSON STRING AND RETURN A LIST OF SEGMENTS

        String segmentString = "\\{\"segments\":";
        if (result.contains(segmentString)) {
            result = result.replaceFirst(segmentString, "");
            result = result.substring(0, result.lastIndexOf("}"));
        }

        Segment[] segmentsArray = gson.fromJson(result, Segment[].class);
        List<Segment> segments = Arrays.asList(segmentsArray);
        return segments;
    }

    @Override
    public SegmentEffort findSegmentEffort(int id) {
        String URL = "https://www.strava.com/api/v3/segment_efforts/" + id;
        String result = getResult(URL);

        SegmentEffort segmentEffort = gson.fromJson(result, SegmentEffort.class);
        return segmentEffort;
    }

    @Override
    public List<Map> findActivityStreams(long activityId, String[] types) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/streams/" + builder.toString();
        String result = getResult(URL);

        List<Map> streamsArray = gson.fromJson(result, List.class);
        return streamsArray;

    }

    @Override
    public List<Stream> findActivityStreams(long activityId, String[] types, String resolution, String series_type) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/activities/" + activityId + "/streams/" + builder.toString() + "?resolution=" + resolution;

        if (series_type != null && !series_type.isEmpty()) {
            URL += "&series_type=" + series_type;
        }

        String result = getResult(URL);

        Stream[] streamsArray = gson.fromJson(result, Stream[].class);
        List<Stream> streams = Arrays.asList(streamsArray);
        return streams;
    }

    @Override
    public List<Stream> findEffortStreams(int id, String[] types) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/segment_efforts/" + id + "/streams/" + builder.toString();
        String result = getResult(URL);

        Stream[] streamsArray = gson.fromJson(result, Stream[].class);
        List<Stream> streams = Arrays.asList(streamsArray);
        return streams;
    }

    @Override
    public List<Stream> findEffortStreams(long id, String[] types, String resolution, String series_type) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/segment_efforts/" + id + "/streams/" + builder.toString() + "?resolution=" + resolution;

        if (series_type != null && !series_type.isEmpty()) {
            URL += "&series_type=" + series_type;
        }

        String result = getResult(URL);

        Stream[] streamsArray = gson.fromJson(result, Stream[].class);
        List<Stream> streams = Arrays.asList(streamsArray);
        return streams;
    }

    @Override
    public List<Stream> findSegmentStreams(long id, String[] types) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/segments/" + id + "/streams/" + builder.toString();
        String result = getResult(URL);

        Stream[] streamsArray = gson.fromJson(result, Stream[].class);
        List<Stream> streams = Arrays.asList(streamsArray);
        return streams;
    }

    @Override
    public List<Stream> findSegmentStreams(long id, String[] types, String resolution, String series_type) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(types[i]);
        }

        String URL = "https://www.strava.com/api/v3/segments/" + id + "/streams/" + builder.toString() + "?resolution=" + resolution;

        if (series_type != null && !series_type.isEmpty()) {
            URL += "&series_type=" + series_type;
        }

        String result = getResult(URL);

        Stream[] streamsArray = gson.fromJson(result, Stream[].class);
        List<Stream> streams = Arrays.asList(streamsArray);
        return streams;
    }

    @Override
    public UploadStatus uploadActivity(String data_type, File file) {
        String URL = "https://www.strava.com/api/v3/uploads";
        String result = getResultUploadActivity(URL, file, data_type);
        Gson gson = new Gson();
        UploadStatus status = gson.fromJson(result, UploadStatus.class);

        return status;
    }

    @Override
    public UploadStatus uploadActivity(String activity_type, String name, String description, int is_private, int trainer, String data_type, String external_id, File file) {
        return null;
    }

    @Override
    public UploadStatus checkUploadStatus(int uploadId) {

        String URL = "https://www.strava.com/api/v3/uploads/" + uploadId;
        String result = getResult(URL);

        UploadStatus status = gson.fromJson(result, UploadStatus.class);

        return status;
    }

    //	static {
    //		ConsoleHandler handler = new ConsoleHandler();
    //		handler.setLevel(Level.ALL);
    //		Logger log = LogManager.getLogManager().getLogger("");
    //		log.addHandler(handler);
    //		log.setLevel(Level.ALL);
    //		System.setProperty("javax.net.debug","all");
    //	}

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    private String getResultUploadActivity(String URL, File activityFile, String type) {
        StringBuilder sb = new StringBuilder();
        String ext = getExtension(activityFile.getName());
        String boundary = "===" + System.currentTimeMillis() + "===";// UNIQUE
        String LINE_FEED = "\r\n";

        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream out = conn.getOutputStream();
            DataOutputStream request = new DataOutputStream(out);

            addField(type, "activity_type", boundary, LINE_FEED, request);

            addField(ext, "data_type", boundary, LINE_FEED, request);

            addFilePart("file", activityFile, boundary, LINE_FEED, request);

            request.writeBytes(LINE_FEED);
            request.writeBytes("--" + boundary + "--" + LINE_FEED);
            request.flush();
            request.close();

            if (conn.getResponseCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode() + " - " + conn.getErrorStream());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return sb.toString();
    }

    private void addField(String value, String name, String boundary, String LINE_FEED, DataOutputStream request) throws IOException {
        request.writeBytes("--" + boundary + LINE_FEED);
        request.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"" + LINE_FEED);
        request.writeBytes(LINE_FEED);
        request.writeBytes(value + LINE_FEED);
        request.flush();
    }

    public void addFilePart(String fieldName, File uploadFile, String boundary, String LINE_FEED, DataOutputStream request) throws IOException {
        String fileName = uploadFile.getName();
        request.writeBytes("--" + boundary + LINE_FEED);
        request.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"" + LINE_FEED);
        request.writeBytes(LINE_FEED);
        request.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            request.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        request.writeBytes(LINE_FEED);
        request.flush();
    }

    private String getResult(String URL) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = null;
            if (proxy != null) {
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return sb.toString();

    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String getResult(String URL, HashMap optionalParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        try {

            Iterator iterator = optionalParameters.keySet().iterator();

            int index = 0;
            while (iterator.hasNext()) {
                if (index == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                String key = (String) iterator.next();
                sb.append(key);
                sb.append("=");
                sb.append(URLEncoder.encode(optionalParameters.get(key).toString(), "UTF-8"));
                index++;
            }

            URI uri = new URI(String.format(sb.toString()));
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();

    }

    private String postResult(String URL) {
        StringBuffer sb = new StringBuffer();
        try {

            String finalUrl = "";

            String[] parsedUrl = URL.split("\\?");
            String params = URLEncoder.encode(parsedUrl[1], "UTF-8").replace("%3D", "=").replace("%26", "&");

            URL url = new URL(parsedUrl[0] + "?" + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());

            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            boolean redirect = false;
            // normally, 3xx is redirect
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = conn.getHeaderField("Location");

                // open the new connnection again
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private String putResult(String URL) {
        StringBuilder sb = new StringBuilder();

        try {
            String finalUrl = "";
            if (URL.contains("?")) {
                String[] parsedUrl = URL.split("\\?");
                String params = URLEncoder.encode(parsedUrl[1], "UTF-8");
                finalUrl = parsedUrl[0] + "?" + params;
            } else {
                finalUrl = URL;
            }

            URL url = new URL(finalUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            if (conn.getResponseCode() != 200 | conn.getResponseCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return sb.toString();

    }

    private String putResult(String URL, HashMap optionalParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        try {

            Iterator iterator = optionalParameters.keySet().iterator();

            int index = 0;
            while (iterator.hasNext()) {
                if (index == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                String key = (String) iterator.next();
                sb.append(key);
                sb.append("=");
                sb.append(URLEncoder.encode(optionalParameters.get(key).toString(), "UTF-8"));
                index++;
            }

            URI uri = new URI(sb.toString());
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();

    }

    private String deleteResult(String URL) {
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        try {

            URI uri = new URI(String.format(sb.toString()));
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            if (conn.getResponseCode() != 204) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(new Date(1701265869));
    }

}
