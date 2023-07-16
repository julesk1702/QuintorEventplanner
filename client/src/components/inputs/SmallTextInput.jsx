import { useState, useEffect } from "react";

export default function SmallTextInput(props) {
    const { maxCharacters, handleInputChange, displayName, name, placeholder, icon, isInput, isTextArea, isPassword, initialValue } = props;
    const [value, setValue] = useState("");

    useEffect(() => {
        if (initialValue) { setValue(initialValue); }
    }, [initialValue]);

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
          {!isTextArea || isInput ? (
            <input
              type={isPassword ? "password" : "text"}
              name={name || "STI"}
              className="form-control border-0 shadow-none"
              id={name || "STI"}
              placeholder={placeholder || "Typ hier je tekst ..."}
              aria-label={displayName || "Kleine tekst input"}
              value={value}
              onChange={(event) => handlePreInputChange(event, (event) => maxCharacterAmountValidationCheck(event, maxCharacters))}
              required
            />
          ) : (
            <textarea
              type="text"
              name={name || "STI"}
              className="form-control mt-2 shadow-none pl-4"
              rows="3"
              id={name || "STI"}
              placeholder={placeholder || "Typ hier je tekst ..."}
              aria-label={displayName || "Kleine tekst input"}
              value={value}
              onChange={(event) => handlePreInputChange(event, (event) => maxCharacterAmountValidationCheck(event, maxCharacters))}
              required
            />
          )}
          {(isTextArea || !isInput) && (
            <style>
              {`
                .text-area-append {
                  position: absolute;
                  right: .5em;
                  bottom: 0;
                  zIndex: 99;
                }
              `}
            </style>
          )}
          {maxCharacters && (
            <div className={`input-group-append ${isTextArea || !isInput ? 'text-area-append' : ''}`}>
                <span
                    className={`input-group-text border-0 bg-transparent ${value.length < maxCharacters ? 'text-muted' : 'text-danger'}`}
                    style={{paddingRight: 0}}>
                <small>{value.length}/{maxCharacters}</small>
                </span>
            </div>
          )}
        </div>
        {(!isTextArea || isInput) && (
          <hr className="mt-2"/>
        )}
      </div>
    )
}
