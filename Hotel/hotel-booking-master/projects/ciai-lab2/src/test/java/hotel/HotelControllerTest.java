package hotel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import booking.Application;
import booking.model.Hotel;
import booking.repository.HotelRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class HotelControllerTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Autowired
	HotelRepository hotels;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	
	@Test
	public void testIndex() throws Exception
	{
		mvc.perform(get("/hotels")).andExpect(status().isOk())
				.andExpect(view().name("hotels/index"));
	}


	@Test
	public void testAddHotel() throws Exception {
		String hotelName = "Salgados"; 
		mvc.perform(post("/hotels")
				.param("id", Integer.toString(0))
                .param("name", hotelName))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/hotels"));;
				
		Hotel hotel = hotels.findByName(hotelName);
		
		Assert.assertTrue(hotel != null);
	}
	
	@Test
	public void testGetOne() throws Exception
	{
		String hotelName = "Marriott"; 
		Hotel hotel = hotels.findByName(hotelName);
		mvc.perform(get("/hotels/"+hotel.getId()))
				.andExpect(view().name("hotels/show"));
	}
	
	@Test
	public void testModel() throws Exception {
		mvc.perform(get("/hotels"))
				.andExpect(model().attributeExists("hotels"));
	}
}
