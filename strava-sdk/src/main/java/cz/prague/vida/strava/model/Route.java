/*
 * Strava API v3
 * The [Swagger Playground](https://developers.strava.com/playground) is the easiest way to familiarize yourself with the Strava API by submitting HTTP requests and observing the responses before you write any client code. It will show what a response will look like with different endpoints depending on the authorization scope you receive from your athletes. To use the Playground, go to https://www.strava.com/settings/api and change your “Authorization Callback Domain” to developers.strava.com. Please note, we only support Swagger 2.0. There is a known issue where you can only select one scope at a time. For more information, please check the section “client code” at https://developers.strava.com/docs.
 *
 * OpenAPI spec version: 3.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package cz.prague.vida.strava.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Route
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2023-11-28T18:17:19.342Z")
public class Route {
  @SerializedName("athlete")
  private SummaryAthlete athlete = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("distance")
  private Float distance = null;

  @SerializedName("elevation_gain")
  private Float elevationGain = null;

  @SerializedName("id")
  private Long id = null;

  @SerializedName("id_str")
  private String idStr = null;

  @SerializedName("map")
  private PolylineMap map = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("private")
  private Boolean _private = null;

  @SerializedName("starred")
  private Boolean starred = null;

  @SerializedName("timestamp")
  private Integer timestamp = null;

  @SerializedName("type")
  private Integer type = null;

  @SerializedName("sub_type")
  private Integer subType = null;

  @SerializedName("created_at")
  private String createdAt = null;

  @SerializedName("updated_at")
  private String updatedAt = null;

  @SerializedName("estimated_moving_time")
  private Integer estimatedMovingTime = null;

  @SerializedName("segments")
  private List<SummarySegment> segments = null;

  public Route athlete(SummaryAthlete athlete) {
    this.athlete = athlete;
    return this;
  }

   /**
   * Get athlete
   * @return athlete
  **/
  @ApiModelProperty(value = "")
  public SummaryAthlete getAthlete() {
    return athlete;
  }

  public void setAthlete(SummaryAthlete athlete) {
    this.athlete = athlete;
  }

  public Route description(String description) {
    this.description = description;
    return this;
  }

   /**
   * The description of the route
   * @return description
  **/
  @ApiModelProperty(value = "The description of the route")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Route distance(Float distance) {
    this.distance = distance;
    return this;
  }

   /**
   * The route&#39;s distance, in meters
   * @return distance
  **/
  @ApiModelProperty(value = "The route's distance, in meters")
  public Float getDistance() {
    return distance;
  }

  public void setDistance(Float distance) {
    this.distance = distance;
  }

  public Route elevationGain(Float elevationGain) {
    this.elevationGain = elevationGain;
    return this;
  }

   /**
   * The route&#39;s elevation gain.
   * @return elevationGain
  **/
  @ApiModelProperty(value = "The route's elevation gain.")
  public Float getElevationGain() {
    return elevationGain;
  }

  public void setElevationGain(Float elevationGain) {
    this.elevationGain = elevationGain;
  }

  public Route id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * The unique identifier of this route
   * @return id
  **/
  @ApiModelProperty(value = "The unique identifier of this route")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Route idStr(String idStr) {
    this.idStr = idStr;
    return this;
  }

   /**
   * The unique identifier of the route in string format
   * @return idStr
  **/
  @ApiModelProperty(value = "The unique identifier of the route in string format")
  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  public Route map(PolylineMap map) {
    this.map = map;
    return this;
  }

   /**
   * Get map
   * @return map
  **/
  @ApiModelProperty(value = "")
  public PolylineMap getMap() {
    return map;
  }

  public void setMap(PolylineMap map) {
    this.map = map;
  }

  public Route name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of this route
   * @return name
  **/
  @ApiModelProperty(value = "The name of this route")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Route _private(Boolean _private) {
    this._private = _private;
    return this;
  }

   /**
   * Whether this route is private
   * @return _private
  **/
  @ApiModelProperty(value = "Whether this route is private")
  public Boolean isPrivate() {
    return _private;
  }

  public void setPrivate(Boolean _private) {
    this._private = _private;
  }

  public Route starred(Boolean starred) {
    this.starred = starred;
    return this;
  }

   /**
   * Whether this route is starred by the logged-in athlete
   * @return starred
  **/
  @ApiModelProperty(value = "Whether this route is starred by the logged-in athlete")
  public Boolean isStarred() {
    return starred;
  }

  public void setStarred(Boolean starred) {
    this.starred = starred;
  }

  public Route timestamp(Integer timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * An epoch timestamp of when the route was created
   * @return timestamp
  **/
  @ApiModelProperty(value = "An epoch timestamp of when the route was created")
  public Integer getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  public Route type(Integer type) {
    this.type = type;
    return this;
  }

   /**
   * This route&#39;s type (1 for ride, 2 for runs)
   * @return type
  **/
  @ApiModelProperty(value = "This route's type (1 for ride, 2 for runs)")
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Route subType(Integer subType) {
    this.subType = subType;
    return this;
  }

   /**
   * This route&#39;s sub-type (1 for road, 2 for mountain bike, 3 for cross, 4 for trail, 5 for mixed)
   * @return subType
  **/
  @ApiModelProperty(value = "This route's sub-type (1 for road, 2 for mountain bike, 3 for cross, 4 for trail, 5 for mixed)")
  public Integer getSubType() {
    return subType;
  }

  public void setSubType(Integer subType) {
    this.subType = subType;
  }

  public Route createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * The time at which the route was created
   * @return createdAt
  **/
  @ApiModelProperty(value = "The time at which the route was created")
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Route updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * The time at which the route was last updated
   * @return updatedAt
  **/
  @ApiModelProperty(value = "The time at which the route was last updated")
  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Route estimatedMovingTime(Integer estimatedMovingTime) {
    this.estimatedMovingTime = estimatedMovingTime;
    return this;
  }

   /**
   * Estimated time in seconds for the authenticated athlete to complete route
   * @return estimatedMovingTime
  **/
  @ApiModelProperty(value = "Estimated time in seconds for the authenticated athlete to complete route")
  public Integer getEstimatedMovingTime() {
    return estimatedMovingTime;
  }

  public void setEstimatedMovingTime(Integer estimatedMovingTime) {
    this.estimatedMovingTime = estimatedMovingTime;
  }

  public Route segments(List<SummarySegment> segments) {
    this.segments = segments;
    return this;
  }

  public Route addSegmentsItem(SummarySegment segmentsItem) {
    if (this.segments == null) {
      this.segments = new ArrayList<SummarySegment>();
    }
    this.segments.add(segmentsItem);
    return this;
  }

   /**
   * The segments traversed by this route
   * @return segments
  **/
  @ApiModelProperty(value = "The segments traversed by this route")
  public List<SummarySegment> getSegments() {
    return segments;
  }

  public void setSegments(List<SummarySegment> segments) {
    this.segments = segments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Route route = (Route) o;
    return Objects.equals(this.athlete, route.athlete) &&
        Objects.equals(this.description, route.description) &&
        Objects.equals(this.distance, route.distance) &&
        Objects.equals(this.elevationGain, route.elevationGain) &&
        Objects.equals(this.id, route.id) &&
        Objects.equals(this.idStr, route.idStr) &&
        Objects.equals(this.map, route.map) &&
        Objects.equals(this.name, route.name) &&
        Objects.equals(this._private, route._private) &&
        Objects.equals(this.starred, route.starred) &&
        Objects.equals(this.timestamp, route.timestamp) &&
        Objects.equals(this.type, route.type) &&
        Objects.equals(this.subType, route.subType) &&
        Objects.equals(this.createdAt, route.createdAt) &&
        Objects.equals(this.updatedAt, route.updatedAt) &&
        Objects.equals(this.estimatedMovingTime, route.estimatedMovingTime) &&
        Objects.equals(this.segments, route.segments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(athlete, description, distance, elevationGain, id, idStr, map, name, _private, starred, timestamp, type, subType, createdAt, updatedAt, estimatedMovingTime, segments);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Route {\n");
    
    sb.append("    athlete: ").append(toIndentedString(athlete)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    elevationGain: ").append(toIndentedString(elevationGain)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idStr: ").append(toIndentedString(idStr)).append("\n");
    sb.append("    map: ").append(toIndentedString(map)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    _private: ").append(toIndentedString(_private)).append("\n");
    sb.append("    starred: ").append(toIndentedString(starred)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    subType: ").append(toIndentedString(subType)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    estimatedMovingTime: ").append(toIndentedString(estimatedMovingTime)).append("\n");
    sb.append("    segments: ").append(toIndentedString(segments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

