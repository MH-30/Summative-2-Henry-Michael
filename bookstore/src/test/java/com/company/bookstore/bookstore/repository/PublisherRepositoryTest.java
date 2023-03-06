@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PublisherRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCreatePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");

        mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(publisher.getName()))
                .andExpect(jsonPath("$.street").value(publisher.getStreet()))
                .andExpect(jsonPath("$.city").value(publisher.getCity()))
                .andExpect(jsonPath("$.state").value(publisher.getState()))
                .andExpect(jsonPath("$.postalCode").value(publisher.getPostalCode()))
                .andExpect(jsonPath("$.phone").value(publisher.getPhone()))
                .andExpect(jsonPath("$.email").value(publisher.getEmail()));
    }

    @Test
    public void testGetPublisherById() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        mockMvc.perform(get("/publishers/{id}", savedPublisher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPublisher.getId()))
                .andExpect(jsonPath("$.name").value(savedPublisher.getName()))
                .andExpect(jsonPath("$.street").value(savedPublisher.getStreet()))
                .andExpect(jsonPath("$.city").value(savedPublisher.getCity()))
                .andExpect(jsonPath("$.state").value(savedPublisher.getState()))
                .andExpect(jsonPath("$.postalCode").value(savedPublisher.getPostalCode()))
                .andExpect(jsonPath("$.phone").value(savedPublisher.getPhone()))
                .andExpect(jsonPath("$.email").value(savedPublisher.getEmail()));
    }

    @Test
    public void testGetAllPublishers() throws Exception {
        List<Publisher> publishers = Arrays.asList(
                new Publisher("Publisher 1", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com"),
                new Publisher("Publisher 2", "456 Main St", "Othertown", "NY", "54321", "111-111-1111", "test2@test.com"),
                new Publisher("Publisher 3", "789 Main St", "Another Town", "FL", "67890", "222-222-2222", "test3@test.com")
        );
        publisherRepository.saveAll(publishers);

        mockMvc.perform(get("/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(publishers.get(0).getName()))
                .andExpect(jsonPath("$[0].street").value(publishers.get(0).getStreet()))
                .andExpect(jsonPath("$[0].city").value(publishers.get(0).getCity()))
                .andExpect(jsonPath("$[0].state").value(publishers.get(0).getState()))
                .andExpect(jsonPath("$[0].postalCode").value(publishers.get(0).getPostalCode()))
                .andExpect(jsonPath("$[0].phone").value(publishers.get(0).getPhone()))
                .andExpect(jsonPath("$[0].email").value(publishers.get(0).getEmail()))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value(publishers.get(1).getName()))
                .andExpect(jsonPath("$[1].street").value(publishers.get(1).getStreet()))
                .andExpect(jsonPath("$[1].city").value(publishers.get(1).getCity()))
                .andExpect(jsonPath("$[1].state").value(publishers.get(1).getState()))
                .andExpect(jsonPath("$[1].postalCode").value(publishers.get(1).getPostalCode()))
                .andExpect(jsonPath("$[1].phone").value(publishers.get(1).getPhone()))
                .andExpect(jsonPath("$[1].email").value(publishers.get(1).getEmail()))
                .andExpect(jsonPath("$[2].id").isNumber())
                .andExpect(jsonPath("$[2].name").value(publishers.get(2).getName()))
                .andExpect(jsonPath("$[2].street").value(publishers.get(2).getStreet()))
                .andExpect(jsonPath("$[2].city").value(publishers.get(2).getCity()))
                .andExpect(jsonPath("$[2].state").value(publishers.get(2).getState()))
                .andExpect(jsonPath("$[2].postalCode").value(publishers.get(2).getPostalCode()))
                .andExpect(jsonPath("$[2].phone").value(publishers.get(2).getPhone()))
                .andExpect(jsonPath("$[2].email").value(publishers.get(2).getEmail()));
    }

    @Test
    public void testUpdatePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        savedPublisher.setName("New Name");
        savedPublisher.setStreet("456 Other St");
        savedPublisher.setCity("Othertown");
        savedPublisher.setState("NY");
        savedPublisher.setPostalCode("54321");
        savedPublisher.setPhone("111-111-1111");
        savedPublisher.setEmail("test2@test.com");

        mockMvc.perform(put("/publishers/{id}", savedPublisher.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedPublisher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPublisher.getId()))
                .andExpect(jsonPath("$.name").value(savedPublisher.getName()))
                .andExpect(jsonPath("$.street").value(savedPublisher.getStreet()))
                .andExpect(jsonPath("$.city").value(savedPublisher.getCity()))
                .andExpect(jsonPath("$.state").value(savedPublisher.getState()))
                .andExpect(jsonPath("$.postalCode").value(savedPublisher.getPostalCode()))
                .andExpect(jsonPath("$.phone").value(savedPublisher.getPhone()))
                .andExpect(jsonPath("$.email").value(savedPublisher.getEmail()));
    }

    @Test
    public void testDeletePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        mockMvc.perform(delete("/publishers/{id}", savedPublisher.getId()))
                .andExpect(status().isOk());

        Optional<Publisher> deletedPublisher = publisherRepository.findById(savedPublisher.getId());
        assertFalse(deletedPublisher.isPresent());
    }
}
