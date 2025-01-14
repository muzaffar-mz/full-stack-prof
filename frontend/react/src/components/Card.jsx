'use client'

import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Tag,
    useColorModeValue,
    Button, useDisclosure,
} from '@chakra-ui/react'
import DeleteCustomerAlertDialog from "./DeleteCustomerAlertDialog.jsx";
import UpdateCustomerDrawer from "./UpdateCustomerDrawer.jsx";

export default function CardWithImage({ id, name, email, age, gender, imageNumber, fetchCustomers }) {
    const randomUserGender = gender === 0 ? "men" : "women";
    const { isOpen, onOpen, onClose } = useDisclosure();

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'lg'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${randomUserGender}/${imageNumber}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'}>
                        <Tag borderRadius={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>Age {age} | {gender === 0? "MALE" : "FEMALE"}</Text>
                    </Stack>
                </Box>

                <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                    <Stack>
                        <UpdateCustomerDrawer
                            initialValues={{ name, email, age }}
                            customerId={id}
                            fetchCustomers={fetchCustomers}
                        />
                    </Stack>
                    <Stack>
                        <Button
                            bg={'red.400'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transform: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            _focus={{
                                bg: 'green.500'
                            }}
                            onClick={onOpen}
                        >
                            Delete
                        </Button>
                    </Stack>
                </Stack>
            </Box>

            <DeleteCustomerAlertDialog
                isOpen={isOpen}
                onClose={onClose}
                id={id}
                fetchCustomers={fetchCustomers}
            />
        </Center>
    )
}