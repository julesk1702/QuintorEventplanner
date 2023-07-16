import { useState, useRef, useEffect } from "react";
import DietsRepository from "../../repositories/DietsRepository";
import Loader from "../loaders/Loader";

export default function SearchDiets(props) {
    const [options, setOptions] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedOptions, setSelectedOptions] = useState([]);
    const [dropdownVisible, setDropdownVisible] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    const dropdownRef = useRef(null);
    const dropdownItemRef = useRef(null);
    const searchInputRef = useRef(null);

    const filteredOptions = options.filter(
        (option) => option.name.toLowerCase().indexOf(searchTerm.toLowerCase()) > -1
    );

    /**
     * Add event listener to handle clicks outside the dropdown in order to close it
     */
    /* eslint-disable */
    useEffect(() => {
        DietsRepository.getDiets()
          .then((response) => {
            setOptions(response.data);
            if (props.initialSelectedOptions) {
                const initialSelectedOptions = props.initialSelectedOptions.map((id) =>
                    response.data.find((option) => option.dietId === id)
                );
                setSelectedOptions([...selectedOptions, ...initialSelectedOptions]);
            }
            setIsLoading(false);
          })
          .catch((error) => {
            console.error(error);
            setIsLoading(false);
          });

        document.addEventListener("click", handleClickOutside);
        return () => {
          document.removeEventListener("click", handleClickOutside);
        };
    }, []);

    /**
     * Handle clicks outside the dropdown in order to close it
     */
    const handleClickOutside = (event) => {
        if (dropdownRef.current) {
            if (!dropdownRef.current.contains(event.target) && !searchInputRef.current.contains(event.target)) {
                setDropdownVisible(false);
            }
        }
    };

    /**
     * Set the search term to the value of the input field
     */
    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    /**
     * Set the dropdown to visible when the search input is focused
     */
    const handleSearchFocus = () => {
        setDropdownVisible(true);
    };

    /**
     * Handle the selection of an option in the dropdown
     * @param {*} option The selected option
     */
    const handleSelect = (option) => {
        if (props.handleOptionSelected) { props.handleOptionSelected(option); }
        if (!selectedOptions.includes(option)) {
            setSelectedOptions([...selectedOptions, option]);
        } else {
            handleDeselect(option);
        }
    };

    /**
     * Handle the deselection of an option in the dropdown
     * @param {*} option The deselected option
     */
    const handleDeselect = (option) => {
        if (props.handleOptionDeselected) { props.handleOptionDeselected(option); }
        const updatedOptions = selectedOptions.filter((selectedOption) => selectedOption !== option);
        setSelectedOptions(updatedOptions);
    };

    return ( !isLoading ? (
        <>
            {/* Searchbar */}
            <form action="" className="mt-4">
                <div className="form-group">
                    <label htmlFor="searchDiets" className="mb-2">{props.title || 'Dieetwensen'}</label>
                    <div className="input-group" ref={searchInputRef}>
                        <input
                            type="text"
                            name="searchDiets"
                            className="form-control shadow-none"
                            placeholder={props.placeholder ? props.placeholder : "Zoek diëten .."}
                            value={searchTerm}
                            onChange={handleSearch}
                            onFocus={handleSearchFocus}
                        />
                        <span className="input-group-text bg-light">
                            <i className="fas fa-search" style={{color: '#c23350'}}></i>
                        </span>
                    </div>
                </div>
            </form>
            {/* Dropdown under the searchbar */}
            { dropdownVisible && (
                <div className="dropdown-menu show" ref={dropdownRef} style={{display: 'block', position: 'static', maxHeight: '200px', overflowY: 'auto', width: `${searchInputRef.current.offsetWidth}px`}}>
                    {options.length > 0 && filteredOptions.map((option) => (
                        <div key={option.dietId} className="form-check" role="button" ref={dropdownItemRef} onClick={(e) => handleSelect(option)}>
                            <input
                                type="checkbox"
                                role="button"
                                className="form-check-input"
                                checked={selectedOptions.includes(option)}
                                readOnly
                            />
                            <label className="form-check-label" role="button">{option.name}</label>
                        </div>
                    ))}
                    {options.length === 0 && (
                        <div className="dropdown-item">Geen diëten gevonden</div>
                    )}
                </div>
            )}
            {/* Selected option badges */}
            { selectedOptions.length > 0 && (
                <div className="mt-2 d-flex flex-wrap gap-2">
                    {selectedOptions.map((option) => (
                        <span key={option.dietId} className="badge bg-transparent text-black border">
                            {option.name} <button className="bg-transparent border-0" style={{fontSize: '1.2em'}} aria-hidden="true" onClick={(e) => handleDeselect(option)}>x</button>
                        </span>
                    ))}
                </div>
            )}
        </>
    ) : <div className="container h-100 text-center">
            <div className="d-flex min-height justify-content-center align-items-center">
                <Loader />
            </div>
        </div>
    );
}
