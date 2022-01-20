package elections.utils;

import elections.dto.request.RegisterVoterDtoRequest;
import elections.errors.ElectionsErrorCode;
import elections.errors.ElectionsException;


public class ValidationUtils {

    public static void validateName(String name) throws ElectionsException {
        if (name == null || name.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validateSurname(String surname) throws ElectionsException {
        if (surname == null || surname.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validateStreet(String street) throws ElectionsException {
        if (street == null || street.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validateHouse(String house) throws ElectionsException {
        if (house == null || house.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validateLogin(String login) throws ElectionsException {
        if (login == null || login.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validatePassword(String password) throws ElectionsException {
        if (password == null || password.isEmpty()) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_WRONG_REGISTER_DATA);
        }
    }

    public static void validateVoter(RegisterVoterDtoRequest voter) throws ElectionsException {
        validateName(voter.getName());
        validateSurname(voter.getSurname());
        validateHouse(voter.getHouse());
        validateStreet(voter.getStreet());
        validateLogin(voter.getLogin());
        validatePassword(voter.getPassword());
    }
}
