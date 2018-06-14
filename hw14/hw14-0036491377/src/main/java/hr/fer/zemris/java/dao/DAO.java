package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import java.util.List;

/**
 * Interface towards the database persistence subsystem.
 *
 * @author matej
 */
public interface DAO {

    /**
     * Gets information about all polls.
     *
     * @return list of all polls
     *
     * @throws DAOException if error occurs
     */
    public List<Poll> getPollsInfo() throws DAOException;

    /**
     * Gets information about specific poll.
     *
     * @param id poll to look up
     *
     * @return the wanted poll
     *
     * @throws DAOException if error occurs
     */
    public Poll getPoll(long id) throws DAOException;

    /**
     * Gets basic information about poll options specified by poll id - id, pollId and title.
     *
     * @param pollId id of poll whose options to get
     *
     * @return basic information about options
     *
     * @throws DAOException if error occurs
     */
    public List<PollOption> getPollOptionsInfo(long pollId) throws DAOException;

    /**
     * Returns full information about poll options specified by poll id.
     *
     * @param pollId id of poll whose options to get
     *
     * @return basic information about options
     *
     * @throws DAOException if error occurs
     */
    public List<PollOption> getPollOptions(long pollId) throws DAOException;
}
