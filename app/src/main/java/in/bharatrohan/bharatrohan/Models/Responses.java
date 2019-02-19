package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

public class Responses {

    private ProfileResponse profileResponse;
    private AvatarResponse avatarResponse;

    public Responses(ProfileResponse profileResponse, AvatarResponse avatarResponse) {
        this.profileResponse = profileResponse;
        this.avatarResponse = avatarResponse;
    }

    public ProfileResponse getProfileResponse() {
        return profileResponse;
    }

    public AvatarResponse getAvatarResponse() {
        return avatarResponse;
    }

    public class ProfileResponse {
        @SerializedName("message")
        private String message;

        public ProfileResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public class AvatarResponse {

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("message")
        private String message;

        public AvatarResponse(String avatar, String message) {
            this.avatar = avatar;
            this.message = message;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getMessage() {
            return message;
        }
    }
}
