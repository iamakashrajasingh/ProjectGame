public class Users {

    String username;
    String password;
    String userId;

    int birdScore=0;
    int animalScore=0;
    int countryScore=0;
    int personScore=0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBirdScore() {
        return birdScore;
    }

    public void setBirdScore(int birdScore) {
        this.birdScore = birdScore;
    }

    public int getAnimalScore() {
        return animalScore;
    }

    public void setAnimalScore(int animalScore) {
        this.animalScore = animalScore;
    }

    public int getCountryScore() {
        return countryScore;
    }

    public void setCountryScore(int countryScore) {
        this.countryScore = countryScore;
    }

    public int getPersonScore() {
        return personScore;
    }

    public void setPersonScore(int personScore) {
        this.personScore = personScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
