package api.requestLibraryAddNewClients;

public class addNewClientsPOJO {
    private String clientId;

    private String[] channelIds;

    public addNewClientsPOJO(String clientId, String[] channelIds) {
        this.clientId = clientId;
        this.channelIds = channelIds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String[] getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(String[] channelIds) {
        this.channelIds = channelIds;
    }

    @Override
    public String toString() {
        return "ClassPojo [clientId = " + clientId + ", channelIds = " + channelIds + "]";
    }
}