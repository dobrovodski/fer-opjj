package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 *
 * @author marcupic
 */
public interface DAO {

    /**
     * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka: id i title.
     *
     * @return listu unosa
     *
     * @throws DAOException u slučaju pogreške
     */
    public List<Poll> getPollsInfo() throws DAOException;

    public Poll getPoll(long id) throws DAOException;

    /**
     * Dohvaća Unos za zadani pollId. Ako unos ne postoji, vraća <code>null</code>.
     */
    public List<PollOption> getPollOptionsInfo(long pollId) throws DAOException;

    public List<PollOption> getPollOptions(long pollId) throws DAOException;

    public PollOption getPollOption(long pollId, long optionId) throws DAOException;
}
