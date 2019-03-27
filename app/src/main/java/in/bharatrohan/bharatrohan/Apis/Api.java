package in.bharatrohan.bharatrohan.Apis;

import in.bharatrohan.bharatrohan.Models.Block;
import in.bharatrohan.bharatrohan.Models.Crops;
import in.bharatrohan.bharatrohan.Models.District;
import in.bharatrohan.bharatrohan.Models.Farm;
import in.bharatrohan.bharatrohan.Models.FarmResponse;
import in.bharatrohan.bharatrohan.Models.Farmer;
import in.bharatrohan.bharatrohan.Models.FeDetails;
import in.bharatrohan.bharatrohan.Models.LoginFarmer;
import in.bharatrohan.bharatrohan.Models.OtpResponse;
import in.bharatrohan.bharatrohan.Models.Responses;
import in.bharatrohan.bharatrohan.Models.States;
import in.bharatrohan.bharatrohan.Models.Tehsil;
import in.bharatrohan.bharatrohan.Models.UpdateFarmer;
import in.bharatrohan.bharatrohan.Models.UserFarmer;
import in.bharatrohan.bharatrohan.Models.Village;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

    @POST("farmer/signup")
    Call<UserFarmer> createUser(
            @Body UserFarmer userFarmer);

    @POST("farmer/login")
    Call<LoginFarmer> loginUser(@Body LoginFarmer loginFarmer);

    @FormUrlEncoded
    @POST("farm")
    Call<FarmResponse> createFarm(@Header("Authorization") String token,
                                  @Field("farm_name") String landName,
                                  @Field("location") String location,
                                  @Field("farm_area") String landArea,
                                  @Field("crop_id") String cropId,
                                  @Field("farmer_id") String farmerId);

    @Multipart
    @POST("farm/{farmId}")
    Call<ResponseBody> uploadKml(@Header("Authorization") String token,
                                 @Path("farmId") String farmid,
                                 @Part MultipartBody.Part kml_map,
                                 @Part MultipartBody.Part kml_image);

    @GET("farmer/{id}")
    Call<Farmer> getFarmerDetail(@Header("Authorization") String token,
                                 @Path("id") String feId);

    @GET("farm/{id}")
    Call<Farm> getFarmDetail(@Header("Authorization") String token,
                             @Path("id") String feId);

    @GET("states")
    Call<States> stateList();

    @GET("state/{id}")
    Call<District> districtList(@Path("id") String id1);

    @GET("district/{id}")
    Call<Tehsil> tehsilList(@Path("id") String id2);

    @GET("tehsil/{id}")
    Call<Block> blockList(@Path("id") String id3);

    @GET("block/{id}")
    Call<Village> villageList(@Path("id") String id4);

    @GET("crops")
    Call<Crops> cropList();

    @Multipart
    @PATCH("farmer/update-avatar/{id}")
    Call<Responses.AvatarResponse> updateAvatar(@Header("Authorization") String token,
                                                @Path("id") String id,
                                                @Part MultipartBody.Part avatar);

    @PATCH("farmer/{id}")
    Call<Responses.ProfileResponse> updateFarmerDetail(@Header("Authorization") String token,
                                                       @Path("id") String id,
                                                       @Body UpdateFarmer farmer);

    @FormUrlEncoded
    @PATCH("farm/{id}")
    Call<ResponseBody> updateFarm(@Header("Authorization") String token,
                                  @Path("id") String id,
                                  @Field("farm_name") String landName,
                                  @Field("location") String location,
                                  @Field("farm_area") String landArea,
                                  @Field("crop_id") String cropId);


    @GET("fe/{id}")
    Call<FeDetails> getFeDetail(@Header("Authorization") String token,
                                @Path("id") String feId);

    @POST("farmer/send-otp")
    @FormUrlEncoded
    Call<OtpResponse> getOtp(@Field("contact") String contact);

    @PATCH("farmer/send-otp")
    @FormUrlEncoded
    Call<OtpResponse> getOtpChangePass(@Field("contact") String contact);

    @PATCH("farmer/password-reset")
    @FormUrlEncoded
    Call<ResponseBody> changePassReq(@Field("contact") String contact,
                                     @Field("otp") String otp,
                                     @Field("password") String pass);
}
