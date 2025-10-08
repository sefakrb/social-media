package scorp.socialmedia.common.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.common.model.entity.BaseModel;
import scorp.socialmedia.common.model.mapper.ICommonMapper;

import java.time.Instant;

@Component
public class CommonMapper implements ICommonMapper {

    @Override
    public void setCreatedAt(BaseModel request) {
        Long currentTimestamp = Instant.now().getEpochSecond();
        request.setCreatedAt(currentTimestamp);
    }
}
