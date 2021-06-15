import { Button, Container, TextField, Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';

import { useState } from 'react';

const useStyles = makeStyles((theme) => ({
    container: {
        padding: theme.spacing(3),
    },
})
);

const CakeForm = () => {

    const [titleEntered, setTitleEntered] = useState('');
    const [descrEntered, setDescrEntered] = useState('');
    const [imageUrlEntered, setImageUrlEntered] = useState('');

    const titleInputChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = e.target.value;
        setTitleEntered(newValue);
    }

    const descrInputChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = e.target.value;
        setDescrEntered(newValue);
    }

    const imageUrlInputChanged = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = e.target.value;
        setImageUrlEntered(newValue);
    }

    const onSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Title: " + titleEntered);
        console.log("Descr: " + descrEntered);
        console.log("ImageURL: " + imageUrlEntered);

        const json = JSON.stringify({
            title: titleEntered,
            desc: descrEntered,
            image: imageUrlEntered
        });

        const ADD_CAKE_URL = "http://localhost:8282/cakes";

        axios.post(ADD_CAKE_URL, json,
        {headers: {
            "Content-Type": "application/json"}
        })
        .then(response => console.log(response.data));

        console.log("JSON " + json);

        // setTitleEntered('');
        // setDescrEntered('');
        // setImageUrlEntered('');
    }

    const classes = useStyles();

    return (
        <Container className={classes.container} maxWidth="xs">
            <form >
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField label="Title" onChange={titleInputChanged} value={titleEntered} />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField label="Description" onChange={descrInputChanged} value={descrEntered} />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField label="Image URL" onChange={imageUrlInputChanged} value={imageUrlEntered} />
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary" onClick={onSubmit} >Save</Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
    )
}

export default CakeForm;

