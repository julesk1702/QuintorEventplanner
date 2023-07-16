import {useCallback, useEffect, useState} from "react";
import { toast } from "react-toastify";
import EventsList from "../components/EventList/EventsList";
import EventsRepository from "../repositories/EventsRepository";
import Loader from "../components/loaders/Loader";

export default function HomePage() {
  const [events, setEvents] = useState([]);
  const getEvents = useCallback(() => EventsRepository.getAllEvents(), []);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    getEvents().then((request) => {
      if (request.status === 0) { toast.error("Netwerkfout"); }
      setEvents(request.data);
      setIsLoading(false);
    });
  }, [getEvents]);

  return isLoading ? <Loader /> : <EventsList events={events}/>;
}
