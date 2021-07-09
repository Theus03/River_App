package dev.kaua.river.Data.Account;

public class DtoAccount {
    private long account_id, id_user, verify, status;
    private String account_id_cry, id_user_cry;
    private String name_user, username, email, phone_user, password, verify_id;
    private String banner_user, profile_image, bio_user, url_user, following, followers, born_date, joined_date;
    private String message;
    private String login_method, token;

    public DtoAccount (){}

    public DtoAccount(String login_method, String password) {
        this.login_method = login_method;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin_method() {
        return login_method;
    }

    public void setLogin_method(String login_method) {
        this.login_method = login_method;
    }

    public String getAccount_id_cry() {
        return account_id_cry;
    }

    public void setAccount_id_cry(String account_id_cry) {
        this.account_id_cry = account_id_cry;
    }

    public String getId_user_cry() {
        return id_user_cry;
    }

    public void setId_user_cry(String id_user_cry) {
        this.id_user_cry = id_user_cry;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getVerify() {
        return verify;
    }

    public void setVerify(long verify) {
        this.verify = verify;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(String phone_user) {
        this.phone_user = phone_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify_id() {
        return verify_id;
    }

    public void setVerify_id(String verify_id) {
        this.verify_id = verify_id;
    }

    public String getBanner_user() {
        return banner_user;
    }

    public void setBanner_user(String banner_user) {
        this.banner_user = banner_user;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getBio_user() {
        return bio_user;
    }

    public void setBio_user(String bio_user) {
        this.bio_user = bio_user;
    }

    public String getUrl_user() {
        return url_user;
    }

    public void setUrl_user(String url_user) {
        this.url_user = url_user;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getBorn_date() {
        return born_date;
    }

    public void setBorn_date(String born_date) {
        this.born_date = born_date;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }
}
