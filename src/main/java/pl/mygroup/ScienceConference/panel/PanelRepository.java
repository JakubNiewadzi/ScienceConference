package pl.mygroup.ScienceConference.panel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanelRepository extends JpaRepository<Panel, Long> {
    @Query("SELECT p FROM Panel p WHERE p.conference.id=:conferenceId")
    List<Panel> findByConferenceId(@Param("conferenceId") Long conferenceId);
    @Modifying
    @Query("Delete FROM Panel p WHERE p.conference.id=:conferenceId")
    void removeAllPanelsFromConference(@Param("conferenceId") Long conferenceId);

}
