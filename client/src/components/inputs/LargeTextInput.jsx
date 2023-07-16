import { useState } from "react";

export default function LargeTextInput(props) {

    const { maxCharacters, handleInputChange, displayName, name, placeholder, icon } = props;
    const [value, setValue] = useState("");

    const handlePreInputChange = (event, validationCheck) => {
        if (validationCheck && !validationCheck(event)) { return; }
        setValue(event.target.value);
        handleInputChange(event);
    };

    // eslint-disable-next-line
    const maxCharacterAmountValidationCheck = (event, maxCharacterAmount) => {
      if (event.target.value.length - 1 >= maxCharacterAmount) { return false; }
      return true;
    };

    return (
        <div className="form-group">
        { displayName && (
            <label htmlFor={name || "STI"}>{displayName}</label>
        )}
        <div className="input-group">
          <div className="input-group-prepend">
            <span className="input-group-text border-0 bg-transparent" style={{paddingLeft: 0}}>
              <i className={icon}></i>
            </span>
          </div>
          <textarea
            type="text"
            name={name || "STI"}
            className="form-control mt-2 shadow-none"
            rows="3"
            id={name || "STI"}
            placeholder={placeholder || "Typ hier je tekst ..."}
            aria-label={displayName || "Kleine tekst input"}
            value={value}
            onChange={(event) => handlePreInputChange(event, (event) => maxCharacterAmountValidationCheck(event, maxCharacters))}
            required
          />
          {maxCharacters && (
            <div className="input-group-append">
              <span
                className={`input-group-text border-0 bg-transparent ${value.length < maxCharacters ? 'text-muted' : 'text-danger'}`}
                style={{paddingRight: 0}}>
                <small>{value.length}/{maxCharacters}</small>
              </span>
            </div>
          )}
        </div>
        <hr className="mt-2"/>
      </div>
    )
}
