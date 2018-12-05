package me.secosme.jwtexample;

import java.util.Optional;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("security")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SecurityService {

    @Inject
    Supplier<Optional<LoginDTO>> login;

    @Context
    HttpServletRequest request;

    @POST
    @Path("login")
    public LoginDTO login(LoginDTO l) {
        request.getSession().setAttribute("logged.user", l);
        return l;
    }

    @GET
    public LoginDTO get() {
        return login.get().orElseGet(() -> {
            System.out.println("session doesn't exist!");
            return new LoginDTO();
        });
    }

    @GET
    @Path("logout")
    public void logout() {
        Optional.ofNullable(request.getSession(false))
            .ifPresent((session) -> {
                System.out.println("session invalidated!");
                session.invalidate();
            });
    }
}
