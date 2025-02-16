package com.robinsonir.fittrack.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class FitTrackRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUser;

        if (principal instanceof UserDetails userDetails) {
           currentUser = userDetails.getUsername();
        } else {
            currentUser = principal.toString();
        }

        FitTrackRevisionEntity fitTrackRevisionEntity = (FitTrackRevisionEntity) revisionEntity;
        fitTrackRevisionEntity.setUsername(currentUser);
    }
}
