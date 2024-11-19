package utb.fai.RESTAPIServer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam Long id) {
        // return userRepository.findById(id)
        //     .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
        //     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        try {
            Optional<MyUser> user = this.userRepository.findById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // TODO: handle exception
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // return new ResponseEntity<>()

    }

    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
        if (!MyUser.isUserDataValid(newUser.getEmail(), newUser.getPhoneNumber(), newUser.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // TODO: implement logic of /createUser endpoint
        userRepository.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/editUser")
    public ResponseEntity<MyUser> editUser(@RequestParam Long id, @RequestBody MyUser editUser) {
        if (!MyUser.isUserDataValid(editUser.getEmail(), editUser.getPhoneNumber(), editUser.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<MyUser> myUser = userRepository.findById(id);
        if (myUser.isPresent()) {
            MyUser user = myUser.get();
            user.setEmail(editUser.getEmail());
            user.setPhoneNumber(editUser.getPhoneNumber());
            user.setName(editUser.getName());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<MyUser> deleteUser(@RequestParam Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<MyUser> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
