import {useCallback, useEffect, useState} from "react";
import IdeasList from "../components/IdeasList/IdeasList";
import IdeasRepository from "../repositories/IdeasRepository";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { toast } from 'react-toastify';
import TextInput from "../components/inputs/SmallTextInput";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

export default function IdeasPage() {
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const [ideas, setIdeas] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const userId = parseInt(localStorage.getItem("userId"));
    const [event, setEvent] = useState({
        userIdea: "",
      });

    const getIdeas = useCallback(() => IdeasRepository.getAllIdeas(), []);

    useEffect(() => {
        setIsLoading(true);
        getIdeas().then((data) => {
            setIdeas(data);
            setIsLoading(false);
        });
    }, [getIdeas]);

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setEvent(prevState => ({...prevState, [name]: value}));
      };

    const handleIdeasReload = async () => {
        setIsLoading(true);
        getIdeas().then((data) => {
            setIdeas(data);
            handleClose();
            setIsLoading(false);
        });
    }

    const createId = async () => {
        await IdeasRepository.createIdea(event.userIdea, userId)
        .then((response) => {
            if(response.status === 200){
                toast.success("Het idee is aangemaakt");
                handleIdeasReload();
            }
        })
        .catch((error) => {
            toast.error("Er is iets misgegaan. Probeer het later opnieuw.");
            console.log(error);
        });
    }

    return (
        <>
            <div className="container py-3">
                <div className="row m-0">
                    <h1>Voorgestelde Ideeën</h1>
                </div>
                {windowWidth >= 992 &&(
                <Button onClick={handleShow} className="button bg-solid border-0 d-flex align-items-center justify-content-between mt-3 mb-3" style={{ width: "90%", maxWidth: "300px", borderRadius: "20px", height: "48px" }}>
                    <p className="mt-2" style={{ fontSize: "1.04rem", fontWeight: "600" }}>
                    Stel een idee voor!
                    </p>
                    <div className="d-flex align-items-center justify-content-center bg-white text-primary rounded-circle" style={{ width: "32px", height: "32px" }}>
                        <FontAwesomeIcon icon={faPlus} size="1x" style= {{color: "#c23350"}}/>
                    </div>
                </Button>
                )}
                { isLoading ? null : <IdeasList ideas={ideas} handleIdeasReload={handleIdeasReload}/> }
            </div>
            {windowWidth < 992 &&(
            <div className="container text-center">
                <div className="fixed-bottom" style={{ marginBottom: "6em" }}>
                    <Button onClick={handleShow} className="button bg-solid border-0 d-flex align-items-center justify-content-between mx-auto" style={{ width: "90%", maxWidth: "300px", borderRadius: "20px", height: "48px" }}>
                    <p className="mt-2" style={{ fontSize: "1.04rem", fontWeight: "600" }}>
                    Stel een idee voor!
                    </p>
                    <div className="d-flex align-items-center justify-content-center bg-white text-primary rounded-circle" style={{ width: "32px", height: "32px" }}>
                        <FontAwesomeIcon icon={faPlus} size="lg" style= {{color: "#c23350"}} />
                    </div>
                    </Button>
                </div>
            </div>
            )}
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Idee aanmaken</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <TextInput
                        maxCharacters={500}
                        handleInputChange={handleInputChange}
                        displayName="Idee voorstellen"
                        name="userIdea"
                        placeholder="Geef een idee voor een evenement!"
                        icon="bi-pencil-square"
                        isTextArea={true}
                    />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Annuleren
                    </Button>
                    <Button style={{backgroundColor: "var(--blue-500)"}} onClick={() => createId()}>
                        Aanmaken
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}
