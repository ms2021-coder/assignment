package net.exercise.web;

import java.util.Collection;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.inject.Inject;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.NanoHTTPD.Response;
import net.exercise.model.Person;
import net.exercise.service.PersonService;

public class NanoHttpdAdapter {
    private static String JSON_CONTENT_TYPE = "application/json";
    private static Logger LOGGER = Logger.getLogger(NanoHttpdAdapter.class.getName());
    private PersonService personService;
    private Gson gson;

    @Inject
    public NanoHttpdAdapter(PersonService personService) {
        this.personService = personService;
        this.gson = new Gson();
    }

    public Response Adapt(IHTTPSession session) {

        if (session.getMethod() != Method.POST) {
            String error = "POST only";
            LOGGER.warning(String.format("%s %s %s", session.getMethod().name(), session.getUri(), error));
            return NanoHTTPD.newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, NanoHTTPD.MIME_PLAINTEXT,
                    error);
        }

        try {
            Integer contentLength = Integer.parseInt(session.getHeaders().get("content-length"));
            byte[] buffer = new byte[contentLength];
            session.getInputStream().read(buffer, 0, contentLength);
            String body = new String(buffer);
            String uri = session.getUri().toLowerCase();

            if (uri.endsWith("/get")) {
                GetRequest gr = this.gson.fromJson(body, GetRequest.class);
                Person p = this.personService.Get(gr.id);
                LOGGER.info(String.format("%s %s person-id:%s %sfound", session.getMethod().name(), session.getUri(),
                        gr.id, p == null ? "not-" : ""));
                return NanoHTTPD.newFixedLengthResponse(Response.Status.OK, JSON_CONTENT_TYPE, this.gson.toJson(p));
            } else if (uri.endsWith("/search")) {
                SearchRequest sr = this.gson.fromJson(body, SearchRequest.class);
                Collection<Person> peeps = this.personService.Search(sr.str);
                LOGGER.info(String.format("%s %s \"%s\" matched:%d records", session.getMethod().name(),
                        session.getUri(), sr.str, peeps == null ? 0 : peeps.size()));
                return NanoHTTPD.newFixedLengthResponse(Response.Status.OK, JSON_CONTENT_TYPE, this.gson.toJson(peeps));
            }

            LOGGER.warning(String.format("%s %s not found", session.getMethod().name(), session.getUri()));
            return NanoHTTPD.newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not Found");
        } catch (Exception ex) {
            LOGGER.severe(String.format("%s %s %d", session.getMethod().name(), session.getUri(), ex.getMessage()));
            return NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Error");
        }
    }

    class GetRequest {
        String id;
    }

    class SearchRequest {
        String str;
    }
}
