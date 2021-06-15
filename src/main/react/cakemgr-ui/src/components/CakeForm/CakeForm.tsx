import { Button, Container, TextField, Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import React from 'react';
  
const useStyles = makeStyles((theme) => ({
    container: {
        padding: theme.spacing(3),
    },
})
);

const CakeForm = () => {

    const classes = useStyles();

    return (
        <Container className={classes.container} maxWidth="xs">
            <form >
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField label="Title" />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField label="Description" />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField label="Image URL" />
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary">Save</Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
    )
}

export default CakeForm;

