package scorp.socialmedia.common.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.common.model.entity.BaseModel;
import scorp.socialmedia.common.model.mapper.ICommonMapper;

import java.time.Instant;
import java.util.Date;


@Component
public class CommonMapper implements ICommonMapper {

    //TODO timestamp to date is here
    @Override
    public void setCreatedAt(BaseModel request) {
        Long currentTimestamp = Instant.now().getEpochSecond();
        request.setCreated_at(currentTimestamp);
        /*
        //timestampt to date
        Instant instant = Instant.ofEpochSecond(request.getCreated_at());
        Date date = Date.from(instant);
        */
    }
}
