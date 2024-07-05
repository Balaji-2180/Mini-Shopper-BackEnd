package minishopper.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minishopper.dto.UserDto;
import minishopper.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends BaseResponse{
	
	private UserDto user;
	

}
