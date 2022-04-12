package api.domains.mno_lookup.repo;

import api.domains.mno_lookup.model.MnoLookupRequest;

public class MnoLookupRequestRepo {
    public static MnoLookupRequest setUpMnoLookupData(int clientId, String msisdn, int countryCallingCode){
        return MnoLookupRequest.builder()
                .clientId(clientId)
                .msisdn (msisdn)
                .countryCallingCode (countryCallingCode)
                //.clientTxnRef("") --optional
                //.channelId(0) --optional
                .build();
    }

}
