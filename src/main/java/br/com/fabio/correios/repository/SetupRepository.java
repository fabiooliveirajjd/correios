package br.com.fabio.correios.repository;

import br.com.fabio.correios.entidade.Address;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.apache.http.HttpEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SetupRepository {

    @Value("${setup.origin.url}")
    private String url;

    public List<Address> getFromOrigin() throws Exception {

        List<Address> resultList = new ArrayList<>();
        String resultStr = "";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(this.url))) {

            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity);

            String[] resultStrSplited = resultStr.split("\n");

            for (String current : resultStr.split("\n")) {
                String[] currentSplited = current.split(",");

                if (currentSplited[0].length() > 2) // breaks the header line, if exists
                    continue;

                resultList.add(Address.builder()
                        .state(currentSplited[0])
                        .city(currentSplited[1])
                        .district(currentSplited[2])
                        .zipcode(StringUtils.leftPad(currentSplited[3], 8, "0"))
                        .street(currentSplited.length > 4 ? currentSplited[4] : null).build());
            }
        }

        return resultList;
    }
}
