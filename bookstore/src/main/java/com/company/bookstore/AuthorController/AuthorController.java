@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepo;
    //Create
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@Requestbody Author author) {
        return authorRepo.save(author);
    }

    // Read by Id
    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable id) {
        Optional<Customer> returnAuthor = authorRepo.findById(id);
        if (returnAuthor.isPresent()) return returnAuthor.get();
        return null;
    }


    // Read All
    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        List<Author> authors = (List<Author>) authorRepo.findAll();
        return authors;
    }

    //Update
    @PutMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@PathVariable id, @RequestBody Author author) {
        Optional<Author> authorOptional = authorRepo.findById(id);
        if (!authorOptional.isPresent()) return;
        authorOptional.setFirstName(author.getFirstName());
        authorOptional.setLastName(author.getLastName());
        authorOptional.setStreet(author.getStreet());
        authorOptional.setCity(author.getCity());
        authorOptional.setState(author.getState());
        authorOptional.setPostalCode(author.getPostalCode());
        authorOptional.setPhone(author.getPhone());
        authorOptionalsetEmail(author.getEmail());
    }

    //Delete
    @DeleteMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable id) {
        Optional<Author> authorOptional = authorRepo.findById(id);
        if (!authorOptional.isPresent()) return;
        authorRepo.deleteById(id);
    }
}