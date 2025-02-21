package cz.prague.vida.strava.api;

import cz.prague.vida.strava.entities.Activity;
import cz.prague.vida.strava.model.ActivityStats;
import cz.prague.vida.strava.model.DetailedActivity;
import cz.prague.vida.strava.model.DetailedAthlete;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static javax.swing.UIManager.get;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */

public class StravaV3ClientTest {

    private static long athleteId;
    long activityId = 0;

    private static StravaV3Client strava;

    //    int updateActivityId;
    //    int clubId;
    //    String gearId;
    //    long segmentId;
    //
    //
    //
    //    @Test
    //    public void testFailedConnection()
    //    {
    //
    //            JStravaV3 strava= new JStravaV3("xxxxxxxx");
    //
    //    }
    //
    //
    @BeforeAll
    public static void setUp() throws IOException, URISyntaxException, InterruptedException {
        Properties properties = new Properties();
        properties.load(new BufferedReader(new FileReader("application-secret.properties")));
        athleteId = Integer.parseInt(properties.getProperty("athleteId"));
        String proxyIp = properties.getProperty("proxyIp");
        String proxyPort = properties.getProperty("proxyPort");
        strava = StravaV3Client.Builder.newInstance()
                .withClientId(properties.getProperty("clientId"))
                .withClientSecret(properties.getProperty("clientSecret"))
                .withRefreshToken(properties.getProperty("refreshToken"))
//				.withProxy(proxyIp != null ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, Integer.parseInt(proxyPort))) : null)
//				.withProxySelector(proxyIp != null ? ProxySelector.of(new InetSocketAddress(proxyIp, Integer.parseInt(proxyPort))) : null)
                .build();
    }

    @Test
    public void testCurrentAthlete() throws Exception {
        DetailedAthlete athlete = strava.getCurrentAthlete();
        System.out.println(athlete);
        assertNotNull(athlete);

    }

    @Test
    public void testFindAthlete() throws Exception {
        DetailedAthlete athlete = strava.findAthlete(athleteId);
        assertNotNull(athlete);
    }

    @Test
    public void testAthleteStats() throws Exception {
        ActivityStats athleteStats = strava.getAthleteStats(athleteId);
        assertNotNull(athleteStats);
        System.out.println(athleteStats);

    }

    @Test
    public void testAfter() throws Exception {

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2010-08-22");
        long epochMilliSecondsAtTime = date.toInstant().toEpochMilli()/ 1000;

        //ZoneId zoneId = ZoneId.of("Europe/Paris");
        //LocalDateTime localDateTime = LocalDateTime.parse("2024-08-22T00:00:00");
        //long epochMilliSecondsAtTime = localDateTime.atZone(zoneId).toInstant().toEpochMilli() / 1000;

        //ZonedDateTime after = ZonedDateTime.of(2024, 8, 22, 0, 0, 0, 0, ZoneId.of("Europe/Vienna"));

        List<DetailedActivity> athleteStats = strava.getCurrentAthleteActivitiesAfter(1, 5, epochMilliSecondsAtTime);
        assertNotNull(athleteStats);
        System.out.println(athleteStats);

    }

    //
    //    @Test
    //    public void testUpdateAthlete() throws Exception {
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //
    //        HashMap optionalParameters= new HashMap();
    //
    //        double weight=71;
    //        optionalParameters.put("weight",weight);
    //        Athlete athlete=strava.updateAthlete(optionalParameters);
    //        assertNotNull(athlete);
    //    }
    //
    //
    //    @Test
    //    public void testFindAthleteKOMs(){
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<SegmentEffort> efforts= strava.findAthleteKOMs(athleteId);
    //
    //        assertFalse(efforts.isEmpty());
    //        for (SegmentEffort effort:efforts)
    //        {
    //            System.out.println("Segment Effort KOM " + effort.toString());
    //
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindAthleteKOMsWithPagination(){
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<SegmentEffort> efforts= strava.findAthleteKOMs(athleteId,2,1);
    //
    //        assertFalse(efforts.isEmpty());
    //        assertTrue(efforts.size()==1);
    //        for (SegmentEffort effort:efforts)
    //        {
    //            System.out.println("Segment Effort KOM " + effort.toString());
    //
    //        }
    //
    //    }
    //
    //
    //
    //    @Test
    //    public void testGetCurrentAthleteFriends() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.getCurrentAthleteFriends();
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Current Athlete Friends "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testGetCurrentAthleteFriendsWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.getCurrentAthleteFriends(2,1);
    //        assertFalse(athletes.isEmpty());
    //        assertTrue(athletes.size()==1);
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Current Athlete Friends "+athlete.toString());
    //        }
    //
    //    }
    //
    //
    //
    //    @Test
    //    public void testFindAthleteFriends() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteFriends(athleteId);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Friends "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindAthleteFriendsWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteFriends(athleteId,2,1);
    //        assertFalse(athletes.isEmpty());
    //        assertTrue(athletes.size()==1);
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Friends with pagination "+athlete.toString());
    //        }
    //
    //    }
    //
    //
    //
    //    @Test
    //    public void testGetCurrentAthleteFollowers() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.getCurrentAthleteFollowers();
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Followers "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testGetCurrentAthleteFollowersWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.getCurrentAthleteFollowers(2,1);
    //        assertTrue(athletes.size()==1);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Followers "+athlete.toString());
    //        }
    //
    //    }
    //
    //
    //
    //
    //    @Test
    //    public void testFindAthleteFollowers() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteFollowers(athleteId);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Followers "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindAthleteFollowersWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteFollowers(athleteId,2,1);
    //        assertTrue(athletes.size()==1);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athlete Followers "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindAthleteBothFollowing() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteBothFollowing(athleteId);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athletes Both Following "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindAthleteBothFollowingWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findAthleteBothFollowing(athleteId,2,1);
    //        assertTrue(athletes.size()==1);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Athletes Both Following "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testCreateAndDeleteActivity() throws Exception {
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //
    //        Activity activity= strava.createActivity("Test Manual Activity", "ride", "2014-03-14T09:00:00Z", 10);
    //        assertNotNull(activity);
    //        System.out.println("Activity Name "+activity.toString());
    //        Activity activityExtra= strava.createActivity("Test Manual Activity","ride","2014-03-14T09:00:00Z",10,"Testing manual creation",100);
    //        assertNotNull(activityExtra);
    //        System.out.println("Activity Name "+activityExtra.toString());
    //        strava.deleteActivity(activity.getId());
    //        strava.deleteActivity(activityExtra.getId());
    //
    //
    //    }
    //
    //
    //
    //

    //    @Test
    //    public void testFindActivity() throws Exception {
    //
    //        StravaV3Client strava = new StravaV3Client();
    //
    //        DetailedActivity activity = strava.findActivity(activityId);
    //        assertNotNull(activity);
    //        System.out.println("Activity Name " + activity.toString());
    //        assertNotNull(activity.getAthlete());
    //        System.out.println("Athlete " + activity.getAthlete().getId());
    //        System.out.println("MAP" + activity.getMap().toString());
    //        for (DetailedSegmentEffort segmentEffort : activity.getSegmentEfforts()) {
    //            System.out.println("Segment Effort " + segmentEffort.toString());
    //            System.out.println("  Segment Effort Athlete" + segmentEffort.getAthlete().getId());
    //            assertNotNull(segmentEffort.getSegment());
    //            System.out.println("        Matching Segment " + segmentEffort.getSegment().toString());
    //        }
    //    }

    //
    //    @Test
    //    public void testUpdateActivity() throws Exception {
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //
    //        HashMap optionalParameters= new HashMap();
    //
    //        double weight=71;
    //        String description="Autodromo mañanero";
    //        String name="Autodromo en la mañana";
    //        optionalParameters.put("description",description);
    //        optionalParameters.put("name",name);
    //        Activity activity=strava.updateActivity(updateActivityId,optionalParameters);
    //        assertNotNull(activity);
    //    }
    //
    //
    //
    @Test
    public void testGetCurrentAthleteActivities() throws URISyntaxException, InterruptedException {
        List<DetailedActivity> activities = strava.getCurrentAthleteActivities();
        assertFalse(activities.isEmpty());
        for (DetailedActivity activity : activities) {
            System.out.println("Current Athlete Activity " + activity.toString());
        }

    }

    //
    //    @Test
    //    public void testGetCurrentAthleteActivitiesWithPagination()
    //    {
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Activity> activities= strava.getCurrentAthleteActivities(2,1);
    //        assertTrue(activities.size()==1);
    //        assertFalse(activities.isEmpty());
    //        for (Activity activity:activities)
    //        {
    //            System.out.println("Current Athlete Activity With Pagination "+activity.toString());
    //        }
    //
    //    }
    //
    //
    //
    //    @Test
    //    public void testGetCurrentFriendsActivities()
    //    {
    //        StravaV3Client strava= new StravaV3Client();
    //        List<DetailedActivity> activities= strava.getCurrentFriendsActivities();
    //        assertFalse(activities.isEmpty());
    //        for (DetailedActivity activity:acdctivities)
    //        {
    //            System.out.println("Friend Activity "+activity.toString());
    //        }
    //    }
    //
    //
    //    @Test
    //    public void testGetCurrentFriendsActivitiesWithPagination()
    //    {
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Activity> activities= strava.getCurrentFriendsActivities(2, 1);
    //        assertTrue(activities.size()==1);
    //        assertFalse(activities.isEmpty());
    //        for (Activity activity:activities)
    //        {
    //            System.out.println("Friend Activity "+activity.toString());
    //        }
    //    }
    //
    //    @Test
    //    public void testFindActivityLaps() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<LapsItem>laps=strava.findActivityLaps(activityId);
    //
    //        assertFalse(laps.isEmpty());
    //
    //        for (LapsItem lap:laps)
    //        {
    //            System.out.println("Lap "+ lap.toString());
    //        }
    //    }
    //
    //
    //
    //    @Test
    //    public void testFindActivityComments() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Comment> comments= strava.findActivityComments(activityId);
    //        assertFalse(comments.isEmpty());
    //        for (Comment comment:comments)
    //        {
    //            System.out.println(comment.getText());
    //        }
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindActivityCommentsWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Comment> comments= strava.findActivityComments(activityId,false,2,1);
    //        assertTrue(comments.size()==1);
    //        assertFalse(comments.isEmpty());
    //        for (Comment comment:comments)
    //        {
    //            System.out.println(comment.getText());
    //        }
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindActivityKudos() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findActivityKudos(activityId);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println(athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindActivityKudosWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findActivityKudos(activityId,2,1);
    //        assertTrue(athletes.size()==1);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println(athlete.toString());
    //        }
    //
    //    }
    //
    //
    //
    //
    //    @Test
    //    public void testFindClubMembers() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findClubMembers(clubId);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Club Member "+athlete.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindClubMembersWithPagination() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Athlete> athletes= strava.findClubMembers(clubId,2,1);
    //        assertTrue(athletes.size()==1);
    //        assertFalse(athletes.isEmpty());
    //        for (Athlete athlete:athletes)
    //        {
    //            System.out.println("Club Member "+athlete.toString());
    //        }
    //
    //    }
    //
    //    ////////Remove EXPECTED annotation if you point to a club you are member of.
    //    @Test
    //    public void testFindClubActivities(){
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Activity> activities= strava.findClubActivities(clubId);
    //        assertFalse(activities.isEmpty());
    //        for (Activity activity:activities)
    //        {
    //            System.out.println("Club Activity Name "+activity.toString());
    //        }
    //
    //    }
    //
    //    ////////Remove EXPECTED annotation if you point to a club you are member of.
    //    @Test
    //    public void testFindClubActivitiesWithPagination(){
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Activity> activities= strava.findClubActivities(clubId,2,1);
    //        assertTrue(activities.size()==1);
    //        assertFalse(activities.isEmpty());
    //        for (Activity activity:activities)
    //        {
    //            System.out.println("Club Activity Name "+activity.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindClub() throws Exception {
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //
    //        Club club= strava.findClub(clubId);
    //        assertNotNull(club);
    //        System.out.println("Club Name " + club.toString());
    //
    //    }
    //
    //
    //    ////////Change assert if you do have clubs
    //    @Test
    //    public void testGetCurrentAthleteClubs(){
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Club> clubs= strava.getCurrentAthleteClubs();
    //        assertTrue(clubs.isEmpty());
    //        for (Club club:clubs)
    //        {
    //            System.out.println("Club Name " + club.toString());
    //        }
    //
    //    }
    //
    //
    //
    //
    //    @Test
    //    public void testFindGear() throws Exception {
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //
    //        Gear gear= strava.findGear(gearId);
    //        assertNotNull(gear);
    //        System.out.println("Gear Name " + gear.toString());
    //
    //    }
    //
    //    @Test
    //    public void testFindSegment() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        Segment segment= strava.findSegment(segmentId);
    //        assertNotNull(segment);
    //
    //        System.out.println("SEGMENT "+segment.toString());
    //
    //    }
    //
    //    @Test
    //    public void testFindCurrentStarredSegments() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Segment>segments=strava.getCurrentStarredSegment();
    //
    //        assertFalse(segments.isEmpty());
    //
    //        for (Segment segment:segments)
    //        {
    //            System.out.println("Starred Segment "+ segment);
    //        }
    //    }
    //
    //    @Test
    //    public void testFindSegmentLeaderBoard() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        SegmentLeaderBoard board= strava.findSegmentLeaderBoard(segmentId);
    //        assertNotNull(board);
    //        for (EntriesItem entry:board.getEntries())
    //        {
    //            System.out.println("Segment LeaderBoard "+entry.toString());
    //        }
    //
    //    }
    //
    //    @Test
    //    public void testFindSegmentLeaderBoardWithParameters() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        HashMap optionalParameters= new HashMap();
    //        optionalParameters.put("gender","F");
    //        optionalParameters.put("page",1);
    //        optionalParameters.put("per_page",3);
    //        SegmentLeaderBoard board= strava.findSegmentLeaderBoard(segmentId,optionalParameters);
    //        assertNotNull(board);
    //
    //
    //        assertTrue(board.getEntries().size()==3);
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindSegmentExplorer() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        double[] bounds= new double[]{37.821362,-122.505373,37.842038,-122.465977};
    //        List<Segment> segments= strava.findSegments(bounds);
    //        assertNotNull(segments);
    //
    //        for (Segment segment:segments)
    //        {
    //            System.out.println("Segment Explorer "+segment.toString());
    //            System.out.println("CLIMB CATEGORY DESCRIPTION"+segment.getClimbCategory());
    //        }
    //
    //    }
    //
    @Test
    public void testFindActivityStreams() throws Exception {
        List<Map> streams = strava.findActivityStreams(9994612344L, new String[]{"latlng", "time", "distance", "heartrate", "watts", "altitude", "cadence", "temp", "moving", "grade_smooth", "velocity_smooth"});
        assertNotNull(streams);

        int size = ((List) streams.get(0).get("data")).size();
//        File file = new File("test.csv");
//        FileWriter fileWriter = new FileWriter(file);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < streams.size(); j++) {
                Map map = (Map) streams.get(j);
                String type = (String) map.get("type");
                if ("time".equals(type) || "altitude".equals(type)) {

                    Double value = (Double) ((List) map.get("data")).get(i);

//                    fileWriter.write(String.valueOf(value));
//                    if ("altitude".equals(type)){
//                        fileWriter.write(",");
//                    }
//                    if ("time".equals(type)){
//                        fileWriter.write("\n");
//                    }

                }
                System.out.print(map.get("type") + ": ");
                System.out.println(((List) map.get("data")).get(i));
            }
            System.out.println("-------------------------------------------------");
        }
//        fileWriter.flush();
//        fileWriter.close();
    }

    @Test
    public void testFindActivityStreams2() throws Exception {
        List<Map> streams = strava.findActivityStreams(9994612344L, new String[]{"latlng", "time", "distance", "heartrate", "watts", "altitude", "cadence", "temp", "moving", "grade_smooth", "velocity_smooth"});
        assertNotNull(streams);

        int size = ((List) streams.get(0).get("data")).size();
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < streams.size(); j++) {
                Map map = (Map) streams.get(j);
                String type = (String) map.get("type");
                Object object = ((List) map.get("data")).get(i);
                if ("latlng".equals(type)) {
                    s.append(", latlng=" + object);
                }
                else if ("time".equals(type)) {
                    s.append(", time=" + object);
                }
                else if ("distance".equals(type)) {
                    s.append(", distance=" + object);
                }
                else if ("heartrate".equals(type)) {
                    s.append(", heartrate=" + object);
                }
                else if ("watts".equals(type)) {
                    s.append(", watts=" + object);
                }
                else if ("altitude".equals(type)) {
                    s.append(", altitude=" + object);
                }
                else if ("cadence".equals(type)) {
                    s.append(", cadence=" + object);
                }
                else if ("temp".equals(type)) {
                    s.append(", temp=" + object);
                }
                else if ("moving".equals(type)) {
                    s.append(", moving=" + object);
                }
                else if ("grade_smooth".equals(type)) {
                    s.append(", grade_smooth=" + object);
                }
                else if ("velocity_smooth".equals(type)) {
                    s.append(", velocity_smooth=" + object);
                }
            }
            System.out.println("saving new stream index=" + i + 1 + s.toString());
            s = new StringBuilder();
        }

    }
    //
    //
    //    @Test
    //    public void testFindActivityStreamsWithResolution() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
    //        assertNotNull(streams);
    //
    //        for (Stream stream:streams)
    //        {
    //            System.out.println("STREAM TYPE "+stream.getType());
    //            for (int i=0;i<stream.getData().size();i++)
    //            {
    //                assertEquals("low",stream.getResolution());
    //                System.out.println("STREAM " + stream.getData().get(i));
    //            }
    //        }
    //
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindEffortStreams() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Stream> streams= strava.findEffortStreams(activityId,new String[]{"latlng","time","distance"});
    //        assertNotNull(streams);
    //
    //        for (Stream stream:streams)
    //        {
    //            System.out.println("STREAM TYPE "+stream.getType());
    //            for (int i=0;i<stream.getData().size();i++)
    //            {
    //                System.out.println("STREAM "+stream.getData().get(i));
    //            }
    //        }
    //
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindEffortStreamsWithResolution() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Stream> streams= strava.findEffortStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
    //        assertNotNull(streams);
    //
    //        for (Stream stream:streams)
    //        {
    //            System.out.println("STREAM TYPE "+stream.getType());
    //            for (int i=0;i<stream.getData().size();i++)
    //            {
    //                assertEquals("low",stream.getResolution());
    //                System.out.println("STREAM " + stream.getData().get(i));
    //            }
    //        }
    //
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindSegmentStreams() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"});
    //        assertNotNull(streams);
    //
    //        for (Stream stream:streams)
    //        {
    //            System.out.println("STREAM TYPE "+stream.getType());
    //            for (int i=0;i<stream.getData().size();i++)
    //            {
    //                System.out.println("STREAM "+stream.getData().get(i));
    //            }
    //        }
    //
    //
    //    }
    //
    //
    //    @Test
    //    public void testFindSegmentStreamsWithResolution() throws Exception{
    //
    //        JStravaV3 strava= new JStravaV3(accessToken);
    //        List<Stream> streams= strava.findActivityStreams(activityId,new String[]{"latlng","time","distance"},"low",null);
    //        assertNotNull(streams);
    //
    //        for (Stream stream:streams)
    //        {
    //            System.out.println("STREAM TYPE "+stream.getType());
    //            for (int i=0;i<stream.getData().size();i++)
    //            {
    //                assertEquals("low",stream.getResolution());
    //                System.out.println("STREAM " + stream.getData().get(i));
    //            }
    //        }
    //
    //
    //    }
    //

}
